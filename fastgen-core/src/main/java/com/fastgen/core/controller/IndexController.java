package com.fastgen.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页-controller
 *
 * @author: zet
 * @date:2019/9/11
 */
@Controller
public class IndexController {

    /**
     * 访问首页方式
     */
    @GetMapping({"/", "/index","generator"})
    String index() {
        return "index";
    }
}
