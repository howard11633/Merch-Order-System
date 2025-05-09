package com.merchordersystem.backend.dto;

import com.merchordersystem.backend.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank //避免null和空字串
    @Email //確保email格式
    private String email;


    @NotBlank
    private String password;
}
