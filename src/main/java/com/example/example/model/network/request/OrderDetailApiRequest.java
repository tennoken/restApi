package com.example.example.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailApiRequest {

    private Long id;

    private String status;

    private Integer quantity;

    private BigDecimal totalPrice;

    private LocalDateTime arrivalDate;

    private Long orderGroupId;

    private Long itemId;

}
