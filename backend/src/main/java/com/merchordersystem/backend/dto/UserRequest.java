package com.merchordersystem.backend.dto;

import com.merchordersystem.backend.model.Gender;
import com.merchordersystem.backend.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotNull
    private String name;

    @NotNull
    private Gender gender;

    @NotNull
    private String email;

    @NotNull
    private Role role;

    @NotNull
    private String password;

}
