package com.luyitian.son.weixin.service;

import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.weixin.output.dto.AppDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags="微信对外接口")
public interface WeiXinService {
    @ApiOperation(value ="显示会员信息")
    @GetMapping("getAppInfo")
    public BaseResponse<AppDto> getAppentity();
}
