package com.luyitian.son.member.mapper.entity;

import com.luyitian.son.base.entity.BaseDo;


import lombok.Data;

@Data
public class UserTokenDo extends BaseDo {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户token
	 */
	private String token;
	/**
	 * 登陆类型
	 */
	private String loginType;

	/**
	 * 设备信息
	 */
	private String deviceInfor;
	/**
	 * 用户userId
	 */
	private Long userId;


}
