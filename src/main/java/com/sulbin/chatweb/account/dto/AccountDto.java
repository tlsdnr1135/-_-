package com.sulbin.chatweb.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class AccountDto {

    @ApiModelProperty(example = "sulbin")
    private String name;

    @ApiModelProperty(example = "1234")
    private String password;

}
