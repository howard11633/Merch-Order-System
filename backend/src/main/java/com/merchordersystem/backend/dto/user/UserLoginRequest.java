package com.merchordersystem.backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {

    @NotBlank //避免null和空字串
    @Email //確保email格式
    private String email;

    @NotBlank
    private String password;

}
