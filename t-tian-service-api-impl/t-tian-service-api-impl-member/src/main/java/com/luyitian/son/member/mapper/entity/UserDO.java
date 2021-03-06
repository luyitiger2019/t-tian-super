package com.luyitian.son.member.mapper.entity;

import java.util.Date;

import com.luyitian.son.base.entity.BaseDo;
import lombok.Data;

/**
 * 数据库访问层
 */
@Data
public class UserDO extends BaseDo {

	/**
	 * userid
	 */

	private Long userId;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 性别 0 男 1女
	 */
	private char sex;
	/**
	 * 年龄
	 */
	private Long age;

	private char isAvalible;
	/**
	 * 用户头像
	 */
	private String picImg;
	/**
	 * 用户关联 QQ 开放ID
	 */
	private String qqOpenid;
	/**
	 * 用户关联 微信 开放ID
	 */
	private String wxOpenid;
}
