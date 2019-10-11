package com.fastgen.sample.action.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础分页请求基类
 *
 * @author: zet
 * @date: 2018/8/22 7:15
 */
@Data
public class PageBaseRequest {

    @ApiModelProperty(value = "第几页")
    private Integer pageNumber = 1;

    @ApiModelProperty(value = "获取的大小数")
    private Integer pageSize = 10;
}

