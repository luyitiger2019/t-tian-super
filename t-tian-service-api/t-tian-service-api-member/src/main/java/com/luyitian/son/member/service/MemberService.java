package com.luyitian.son.member.service;

import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.member.output.dto.UserOutDTO;
import com.luyitian.son.weixin.output.dto.AppDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员服务接口
 */
@Api(tags="会员服务实现接口")
public interface MemberService {
    /**
     * 会员服务调用微信接口
     * @return 会员信息
     */
    @ApiOperation(value="会员服务调用微信服务")
    @GetMapping("getmemberInvokeWeixin")
    public BaseResponse<AppDto> memberInvokeWeixin();
    /**
     * 根据手机号码查询是否已经存在,如果存在返回当前用户信息
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "根据手机号码验证注册码是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码"), })
    @PostMapping("/existMobile")
    BaseResponse<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);
}
