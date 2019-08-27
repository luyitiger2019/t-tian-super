package com.luyitian.son.member.service.impl;

import com.luyitian.son.base.api.BaseApiService;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.constants.Constants;
import com.luyitian.son.member.mapper.UserMapper;
import com.luyitian.son.member.output.dto.UserOutDTO;
import com.luyitian.son.member.service.MemberService;
import com.luyitian.son.member.fengin.WeiXinServiceFeign;
import com.luyitian.son.weixin.output.dto.AppDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {
    @Autowired
    WeiXinServiceFeign weiXinServiceFeign;
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseResponse<AppDto> memberInvokeWeixin() {
        return weiXinServiceFeign.getAppentity();
    }

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        //验证参数
        if(StringUtils.isEmpty(mobile))
        {
            return setResultError("手机号码不能为空");
        }
        //验证手机号码是否存在，用数据库来查询，需要单独定义CODE，表示用户不存在
        UserOutDTO userDTO = userMapper.existMobile(mobile);
         if(userDTO==null)
         {
             return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203,"用户信息不存在");
         }
        return setResultSuccess(userDTO);
    }
}
