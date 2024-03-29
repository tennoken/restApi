package com.example.example.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiRequest {

    private Long id;

    private String account;

    private String password;

    private String status;

    private String role;

    private LocalDateTime lastLoginAt;

    private Integer loginFailCount;

    private LocalDateTime passwordUpdateAt;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;


}
