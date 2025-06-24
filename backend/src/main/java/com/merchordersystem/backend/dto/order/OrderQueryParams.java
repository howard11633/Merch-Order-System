package com.merchordersystem.backend.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderQueryParams {

    private Integer userId;
    private Integer limit;
    private Integer offset;

}
