package com.luyitian.son.weixin.service.impl;

import com.luyitian.son.base.api.BaseApiService;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.constants.Constants;
import com.luyitian.son.weixin.service.VerificaCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.TimeUnit;


@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {

	@Autowired
	private final StringRedisTemplate stringRedisTemplate;

	public VerificaCodeServiceImpl(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}


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
	public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixinCode) {
		// 1.验证码参数是否为空
		if (StringUtils.isEmpty(phone)) {
			return setResultError("手机号码不能为空!");
		}
		if (StringUtils.isEmpty(weixinCode)) {
			return setResultError("注册码不能为空!");
		}
		// 2.根据手机号码查询redis返回对应的注册码
		String weixinCodeKey = Constants.WEIXINCODE_KEY + phone;
		String redisCode = getString(weixinCodeKey);
		if (StringUtils.isEmpty(redisCode)) {
			return setResultError("注册码可能已经过期!!");
		}
		// 3.redis中的注册码与传递参数的weixinCode进行比对
		if (!redisCode.equals(weixinCode)) {
			return setResultError("注册码不正确");
		}
		// 移出对应验证码
		delKey(weixinCodeKey);
		return setResultSuccess("验证码比对正确");
	}

}
