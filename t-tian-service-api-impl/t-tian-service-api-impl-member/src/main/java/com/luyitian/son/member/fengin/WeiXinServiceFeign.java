package com.luyitian.son.member.fengin;

import com.luyitian.son.weixin.service.WeiXinService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("t-tian-weixin")
public interface WeiXinServiceFeign extends WeiXinService {
}
