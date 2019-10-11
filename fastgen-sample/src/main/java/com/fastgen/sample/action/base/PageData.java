package com.fastgen.sample.action.base;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页数据封装类
 * Created by zet on 2019/4/19.
 */
public class PageData<T> {
    @ApiModelProperty("数据总条数")
    private Long totalElements;
    @ApiModelProperty("数据")
    private List<T> content;

    public PageData() {
    }

    public PageData(Long totalElements, List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
    }
    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> PageData<T> parse(Long totalElements, List<T> list) {
        PageData<T> result = new PageData<T>();
        result.setTotalElements(totalElements);
        result.setContent(list);
        return result;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}

