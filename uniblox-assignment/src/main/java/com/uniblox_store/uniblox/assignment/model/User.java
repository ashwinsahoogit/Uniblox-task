package com.uniblox_store.uniblox.assignment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class User {
    @NotNull(message = "User ID cannot be null")
    @NotBlank(message = "User ID cannot be empty")
    private String id;

    @NotNull(message = "User name cannot be null")
    @NotBlank(message = "User name cannot be empty")
    private String name;

    @NotNull(message = "User type cannot be null")
    @NotBlank(message = "User type cannot be empty")
    @Pattern(regexp = "^(user|admin)$", message = "User type must be either 'user' or 'admin'")
    private String type; // user or admin
}
