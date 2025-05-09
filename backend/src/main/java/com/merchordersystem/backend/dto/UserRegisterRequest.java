package com.merchordersystem.backend.dto;

import com.merchordersystem.backend.model.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @NotNull
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
