package com.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsResponse {
    private String name;  // Maps to "fullName" in frontend
    private String phone_number;  // Maps to "phone" in frontend
    private String address;
    private String email;
}
