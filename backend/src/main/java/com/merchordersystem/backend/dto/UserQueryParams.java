package com.merchordersystem.backend.dto;

import com.merchordersystem.backend.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryParams {
    private Role role;
    private String search;
}
