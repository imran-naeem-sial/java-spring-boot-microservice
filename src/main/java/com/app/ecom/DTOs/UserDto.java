package com.app.ecom.DTOs;


import com.app.ecom.Enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole userRole;

    private AddressDTO address;
}
