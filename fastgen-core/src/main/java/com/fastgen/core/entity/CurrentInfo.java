package com.fastgen.core.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 当前信息
 *
 * @author: zet
 * @date:2019/10/17
 */
@Builder
@Data
public class CurrentInfo implements Serializable {
    /**
     * Id
     */
    private Long id;
    /**
     * 当前项目Id
     */
    private String currentProjectId;
    /**
     * 最新活跃时间
     */
    private Date lastActiveTime;
}
