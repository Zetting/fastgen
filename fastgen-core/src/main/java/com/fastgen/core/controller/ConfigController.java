package com.fastgen.core.controller;

import cn.hutool.json.JSONUtil;
import com.fastgen.core.base.Response;
import com.fastgen.core.contract.vo.GenConfig;
import com.fastgen.core.service.ConfigService;
import com.fastgen.core.util.ConfigUtil;
import com.fastgen.core.base.Contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置管理
 *
 * @author: zet
 * @date: 2019/9/11 23:32
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ConfigUtil configUtil;

    /**
     * 获取配置
     *
     * @return
     */
    @GetMapping("/getConfig")
    public Response<GenConfig> getConfig() {
        GenConfig config = configUtil.getConfigBean(Contants.USER_CFG);
        return Response.success(config, "获取成功");
    }

    /**
     * 获取ftl配置文件名
     *
     * @return
     */
    @GetMapping("/getFtlNames")
    public Response<List<String>> getFtlNames() {
        List<String> names = configService.templateNames();
        return Response.success(names, "获取成功");
    }

    /**
     * 更新配置
     *
     * @param genConfig
     * @return
     */
    @PostMapping(value = "/updateConfig")
    public Response genConfig(@Validated @RequestBody GenConfig genConfig) {
        configUtil.insertConfig(Contants.USER_CFG, JSONUtil.toJsonStr(genConfig));
        return Response.success();
    }
}
