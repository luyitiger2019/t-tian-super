package com.luyitian.son.weixin.service.impl;

import com.luyitian.son.base.api.BaseApiService;
import com.luyitian.son.base.entity.BaseResponse;
import com.luyitian.son.weixin.entity.AppEntity;
import com.luyitian.son.weixin.service.WeiXinService;
import org.springframework.web.bind.annotation.RestController;

/** 没有视图层，所以叫SERVICE   实现中不需要写入写SPRING mvc注解 *
 * 微信接口的实现
 */
@RestController
public class WeiXinServiceImpl  extends BaseApiService<AppEntity> implements WeiXinService {
  @Override
  public BaseResponse<AppEntity> getAppentity() {
    return setResultSuccess(new AppEntity("111", "222"));
  }
    }
