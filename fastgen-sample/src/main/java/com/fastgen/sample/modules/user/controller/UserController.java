package com.fastgen.sample.modules.user.controller;

import com.fastgen.sample.action.base.Response;
import com.fastgen.sample.modules.user.contract.req.UserInfoReq;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zet
 * @date:2019/10/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping(value = "/v1/userInfo")
    public Response userInfo(@Validated UserInfoReq req) {
        System.out.println(req);
        return Response.success();
    }
}
