package com.dharbor.sales.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NewSaleDto {

    private UUID userId;
    private String productId;
    private Integer quantity;

}
