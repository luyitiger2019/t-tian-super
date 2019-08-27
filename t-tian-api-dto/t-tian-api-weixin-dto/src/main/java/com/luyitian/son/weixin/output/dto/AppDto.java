package com.luyitian.son.weixin.output.dto;

import lombok.Data;

/**
 * 
 * 
 * @description: App实体类层

 */
@Data
public class AppDto {

	/**
	 * appid
	 */
	private String appId;
	/**
	 * 应用名称
	 */
	private String appName;

	public AppDto() {

	}

	public AppDto(String appId, String appName) {
		super();
		this.appId = appId;
		this.appName = appName;
	}

}
