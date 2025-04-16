package com.dharbor.sales.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Alex Choque
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    private String code;

    private String message;
}
