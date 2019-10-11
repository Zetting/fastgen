package com.fastgen.sample.modules.user.contract.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author: zet
 * @date:2019/10/11
 */
@Data
public class UserInfoReq {

    @NotBlank
    @ApiModelProperty(value = "用户Id", required = true)
    private String userId;
}
