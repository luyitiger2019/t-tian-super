package com.luyitian.son.weixin.mp.handler;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.constants.Constants;
import com.luyitian.son.member.entity.UserEntity;
import com.luyitian.son.utils.RegexUtils;
import com.luyitian.son.weixin.feign.MemberServiceFeign;
import com.luyitian.son.weixin.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class MsgHandler extends AbstractHandler {
   @Value("${luyitianyu.weixin.registration.code.message}")
    private String registrationCodeMessage;
	@Value("${luyitianyu.weixin.default.registration.code.message}")
    private String defaultRegistrationCodeMessage;
	@Autowired
	private final StringRedisTemplate stringRedisTemplate;
	public MsgHandler(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}
	@Autowired
	private MemberServiceFeign memberServiceFeign;

	/**
	 * 存放string类型
	 *
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 * @param timeout
	 *            超时间
	 */
	public void setString(String key, String data, Long timeout) {
		stringRedisTemplate.opsForValue().set(key, data);
		if (timeout != null) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 存放string类型
	 *
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 */
	public void setString(String key, String data) {
		setString(key, data, null);
	}

	/**
	 * 根据key查询string类型
	 *
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		return value;
	}

	/**
	 * 根据对应的key删除key
	 *
	 * @param key
	 */
	public void delKey(String key) {
		stringRedisTemplate.delete(key);
	}
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		try {
			if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
					&& weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
				return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}

		//!.获取微信客户端发送的消息
		String fromContent = wxMessage.getContent();
	   //2.使用正则表达式验证消息是否为手机号码格式
		if(RegexUtils.checkMobile(fromContent)){
		// 3. 如果是手机号码格式的话，随机生成6位注册码
			// 1.根据手机号码调用会员服务接口查询用户信息是否存在
			BaseResponse<UserEntity> reusltUserInfo = memberServiceFeign.existMobile(fromContent);
			if (reusltUserInfo.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
				return new TextBuilder().build("该手机号码" + fromContent + "已经存在!", wxMessage, weixinService);
			}
			if (!reusltUserInfo.getRtnCode().equals(Constants.HTTP_RES_CODE_EXISTMOBILE_203)) {
				return new TextBuilder().build(reusltUserInfo.getMsg(), wxMessage, weixinService);
			}
			int registCode=registCode();
			String content= registrationCodeMessage.format(registrationCodeMessage, registCode);
		//4. 将手机号key和验证码code存入到REDIS中

			setString(Constants.WEIXINCODE_KEY+fromContent,String.valueOf(registCode), Constants.WEIXINCODE_TIMEOUT);
			return new TextBuilder().build(content, wxMessage, weixinService);
		}
		//默认情况下返回默认消息，调用第三方接口或做人机接口
		return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);

	}
	//获取注册码
	private int registCode()
	{
		int registCode=(int)(Math.random()*900000+100000);
		return registCode;
	}


}
