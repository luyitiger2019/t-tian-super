package com.luyitian.son.member.service;

import com.alibaba.fastjson.JSONObject;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.member.input.dto.UserInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags="会员注册接口")
public interface MemberRegisterService {
    @PostMapping("/register")
    @ApiOperation(value="会员注册接口")
    BaseResponse<JSONObject> register(@RequestBody UserInpDTO userEntity, @RequestParam("registerCode") String registerCode);
}
