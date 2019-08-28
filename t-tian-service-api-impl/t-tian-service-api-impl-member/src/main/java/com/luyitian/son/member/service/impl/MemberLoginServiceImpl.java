package com.luyitian.son.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luyitian.son.base.api.BaseApiService;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.constants.Constants;
import com.luyitian.son.member.input.dto.UserLogInputDTO;
import com.luyitian.son.member.mapper.UserMapper;
import com.luyitian.son.member.mapper.UserTokenMapper;
import com.luyitian.son.member.mapper.entity.UserDO;
import com.luyitian.son.member.mapper.entity.UserTokenDo;
import com.luyitian.son.member.service.MemberLoginService;
import com.luyitian.son.utils.MD5Util;
import com.luyitian.son.utils.tonken.GenerateToken;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {
   @Autowired
    private UserMapper userMapper;
   @Autowired
    private GenerateToken generateToken;
   @Autowired
    private UserTokenMapper userTokenMapper;
    @Override
    public BaseResponse<JSONObject> login(@RequestBody  UserLogInputDTO userLoginInpDTO) {
       //1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if(StringUtils.isEmpty(mobile))
        {
            return  setResultError("手机号码不能为空");
        }
        String password = userLoginInpDTO.getPassword();
        if(StringUtils.isEmpty(password))
        {
            return setResultError("密码不能为空");
        }
        //2. 对登录密码进行加密,需要加密后对比
        String newPassword= MD5Util.MD5(password);
        String loginType = userLoginInpDTO.getLoginType();
        if(StringUtils.isEmpty(loginType))
        {
            return setResultError("登录类型不能为空");
        }
        /**
         * 只允许从PC,ANDROID,IOS上登录，并且通一个时候只允许一个设备登录（唯一登录）
         * 目的是现在登录范围
         */
        if(!(loginType.equalsIgnoreCase(Constants.MEMBER_LOGIN_TYPE_ANDROID
                 ) || loginType.equalsIgnoreCase(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equalsIgnoreCase(Constants.MEMBER_LOGIN_TYPE_PC)))
        {
            return setResultError("登录环境存在问题，请确认你的登录设备");
        }
        //3.手机号码加密码进行登录,判断用户是否存在
        UserDO userDO = userMapper.login(mobile,newPassword);
        if(null==userDO)
        {
            return setResultError("用户名称或密码错误");
        }
        //用户登录TONKEN,session,TONKEN相当于SESSION的ID，
        // 用户每一个端登录成功都会生成一个令牌，是零时的，而且唯一的，存在REDIS中作为REDISkey
        //4.生成TONKEN
        Long userId = userDO.getUserId();
         //5.根据USERID+logintype+isavalie查询当前登录类型的登陆过没有
        UserTokenDo userTokenDo = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);
       if(null!=userTokenDo)
       {
           String token = userTokenDo.getToken();
           //从缓存中删除TONKEN
           Boolean isremoveTonken = generateToken.removeToken(token);
           if(isremoveTonken)
           {
               //将TONKEN的状态改为1
               userTokenMapper.updateTokenAvailability(token);
           }
       }

        //6.获取令牌,令牌前缀+登录类型。保证在REDIS的值为USERid
        String keyprefix=Constants.MEMBER_TOKEN_KEYPREFIX+loginType;
        String  newTonken= generateToken.createToken(keyprefix, userId+"");
        UserTokenDo userToken=new UserTokenDo();
        userToken.setUserId(userId);
        userToken.setLoginType(userLoginInpDTO.getLoginType());
        userToken.setToken(newTonken);
        userToken.setDeviceInfor(userLoginInpDTO.getDeviceInfor());
        userTokenMapper.insertUserToken(userToken);
        JSONObject data=new JSONObject();
        data.put("userToken", newTonken);
        //1.插入新的tonken

        return setResultSuccess(data);
    }
}
