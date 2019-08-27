package com.luyitian.son.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luyitian.son.base.api.BaseApiService;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.constants.Constants;
import com.luyitian.son.member.entity.UserEntity;
import com.luyitian.son.member.fengin.VerificaCodeServiceFeign;
import com.luyitian.son.member.mapper.UserMapper;
import com.luyitian.son.member.service.MemberRegisterService;
import com.luyitian.son.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {
    private final UserMapper userMapper;

    @Autowired
    public MemberRegisterServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;
    @Transactional
    @Override
    public BaseResponse<JSONObject> register(@RequestBody  UserEntity userEntity, String registerCode) {

       //1.参数验证，验证注册码是否正确
        String userName=userEntity.getUserName();
        if(StringUtils.isEmpty(userName))
        {
            return setResultError("用户名称不能为空");
        }
        String mobile=userEntity.getMobile();
        if(StringUtils.isEmpty(userName))
        {
            return setResultError("手机号码不能为空");
        }
        //确认验证码是否正确
        String password=userEntity.getPassword();
        if(StringUtils.isEmpty(password))
        {
            return setResultError("密码不能为空");
        }
     //注册码是否正确,会员调用微信实现注册码验证
        BaseResponse<JSONObject> verificaWeixinCode = verificaCodeServiceFeign.verificaWeixinCode(mobile, registerCode);
        System.out.println("---------------------"+verificaWeixinCode.getRtnCode());
        if(!verificaWeixinCode.getRtnCode().equals(Constants.HTTP_RES_CODE_200))
        {
           // setResultError(verificaWeixinCode.getMsg());
            return setResultError(verificaWeixinCode.getMsg());
        }
        //用户密码加密
        String newPassowrd = MD5Util.MD5(password);
        userEntity.setPassword(newPassowrd);
        return userMapper.register(userEntity)>0?setResultSuccess("注册成功")
                      :setResultError("注册失败");
    }
}
