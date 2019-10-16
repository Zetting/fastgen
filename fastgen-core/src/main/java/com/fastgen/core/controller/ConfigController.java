package com.fastgen.core.controller;

import com.fastgen.core.base.Response;
import com.fastgen.core.model.BaseConfigInfo;
import com.fastgen.core.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 配置-控制器
 *
 * @author: zet
 * @date: 2019/9/11 23:32
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    /**
     * 获取当前项目ftl文件名集
     *
     * @return
     */
    @GetMapping("/getFtlNames")
    public Response<List<String>> getFtlNames() {
        List<String> names = configService.templateNames();
        return Response.success(names, "获取成功");
    }

    /**
     * 获取当前项目配置
     *
     * @return
     */
    @GetMapping("/getCustomConfig")
    public Response<Map> getCustomConfig() {
        Map config = configService.getCustomConfig();
        return Response.success(config, "获取成功");
    }

    /**
     * 更新当前项目配置
     *
     * @return
     */
    @PostMapping(value = "/updateCustomConfig")
    public Response updateCustomConfig(@Validated @RequestBody Map<String, Object> configs) {
        return configService.updateCustomConfig(configs);
    }

    /**
     * 获取基础配置
     *
     * @return 配置
     */
    @GetMapping("/getBaseConfig")
    public Response<BaseConfigInfo> getBaseConfig() {
        BaseConfigInfo config = configService.getBaseConfig();
        return Response.success(config, "获取成功");
    }

    /**
     * 更新基础配置
     *
     * @param config 配置
     * @return
     */
    @PostMapping(value = "/updateBaseConfig")
    public Response updateBaseConfig(@Validated @RequestBody BaseConfigInfo config) {
        return configService.updateBaseConfig(config);
    }
}
