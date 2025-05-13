package com.merchordersystem.backend.dto.user;

import com.merchordersystem.backend.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryParams {
    private Role role;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
