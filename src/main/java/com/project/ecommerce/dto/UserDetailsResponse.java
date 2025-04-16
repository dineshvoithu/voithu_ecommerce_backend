package com.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsResponse {
    private String fullName;
    private String phone;
    private String address;
    private String email;
}
