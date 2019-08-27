package com.luyitian.son.base.entity;

import lombok.Data;

/**
 * 
 * 
 * @description:微服务接口封装,T为返回接口data的数据类型
 * @author: 97后互联网架构师-余胜军
 * @contact: QQ644064779、微信yushengjun644 www.mayikt.com
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Data
public class BaseResponse<T> {

	private Integer rtnCode;
	private String msg;
	private T data;

	public BaseResponse() {

	}

	public BaseResponse(Integer rtnCode, String msg, T data) {
		super();
		this.rtnCode = rtnCode;
		this.msg = msg;
		this.data = data;
	}

}
