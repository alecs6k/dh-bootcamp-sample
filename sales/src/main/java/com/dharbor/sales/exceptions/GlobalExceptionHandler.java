package com.dharbor.sales.exceptions;

import com.dharbor.sales.api.response.ErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Alex Choque
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaleNotCompletedException.class)
    public ResponseEntity<ErrorResponse> handleSaleNotCompleted(SaleNotCompletedException ex) {
        log.error("SaleNotCompletedException: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("SALE_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignExceptions(FeignException ex) {
        log.error("FeignException: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse("EXTERNAL_SERVICE_ERROR", "Error calling external service"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unhandled Exception", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Unexpected error occurred"));
    }
}
