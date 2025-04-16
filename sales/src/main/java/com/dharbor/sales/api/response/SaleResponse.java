package com.dharbor.sales.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Alex Choque
 */
@Data
@AllArgsConstructor
public class SaleResponse {

    private String userName;

    private String reservationId;

    private String notificationMessage;
}
