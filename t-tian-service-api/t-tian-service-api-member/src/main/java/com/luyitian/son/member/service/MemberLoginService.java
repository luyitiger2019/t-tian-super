package com.luyitian.son.member.service;

import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.member.input.dto.UserLogInputDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户登陆服务接口")
public interface MemberLoginService {
	/**
	 * 用户登录接口
	 * @param userLoginInpDTO
	 * @return
	 */
	@PostMapping("/login")
	@ApiOperation(value = "会员用户登陆信息接口")
	BaseResponse<JSONObject> login(@RequestBody UserLogInputDTO userLoginInpDTO);

}
