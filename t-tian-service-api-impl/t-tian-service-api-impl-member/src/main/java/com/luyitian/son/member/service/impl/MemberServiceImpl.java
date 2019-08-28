package com.luyitian.son.member.service.impl;

import com.luyitian.son.base.api.BaseApiService;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.constants.Constants;
import com.luyitian.son.member.mapper.UserMapper;
import com.luyitian.son.member.mapper.entity.UserDO;
import com.luyitian.son.member.output.dto.UserOutDTO;
import com.luyitian.son.member.service.MemberService;
import com.luyitian.son.member.fengin.WeiXinServiceFeign;
import com.luyitian.son.utils.bean.LuyiBeanUtils;
import com.luyitian.son.utils.tonken.GenerateToken;
import com.luyitian.son.utils.type.TypeCastHelper;
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
    @Autowired
    private GenerateToken generateToken;
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
        UserDO userDO = userMapper.existMobile(mobile);
         if(userDO==null)
         {
             return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203,"用户信息不存在");
         }

        return setResultSuccess(LuyiBeanUtils.doToDto(userDO, UserOutDTO.class));
    }

    @Override
    public BaseResponse<UserOutDTO> getUserInfo(String tonken) {
        //验证TOKEN参数
        if(StringUtils.isEmpty(tonken))
        {
            return setResultError("TONKEN参数不能为空");
        }
        //使用token查询redis中的userID
        String redisUserId = generateToken.getToken(tonken);
        System.out.println("---------------------"+redisUserId+"-----------------------");
        if(StringUtils.isEmpty(redisUserId))
        {
            return setResultError("TONKEN已经失效或已经失效");
        }
        //使用userId查询数据库
        long userId = TypeCastHelper.toLong(redisUserId);
        UserDO userDO = userMapper.findByUserId(userId);
        if(null==userDO)
        {
            return setResultError("用户名不存在");
        }
        if(0==userDO.getIsAvalible())
        {
            return setResultError("该用户已经被封号");
        }
        return setResultSuccess(LuyiBeanUtils.doToDto(userDO, UserOutDTO.class));
    }
}
