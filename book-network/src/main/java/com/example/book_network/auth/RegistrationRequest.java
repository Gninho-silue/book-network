package com.example.book_network.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is required")
    @NotBlank(message = "Firstname should not be blank")
    private String firstName;
    @NotEmpty(message = "Lastname is required")
    @NotBlank(message = "Lastname should not be blank")
    private String lastName;
    @Email(message = "Enter a valid email")
    @NotEmpty(message = "Email is required")
    @NotBlank(message = "Email should not be blank")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;
}
