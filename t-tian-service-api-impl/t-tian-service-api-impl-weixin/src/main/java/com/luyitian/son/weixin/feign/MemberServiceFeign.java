package com.luyitian.son.weixin.feign;

import com.luyitian.son.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;



@FeignClient("t-tian-member")
public interface MemberServiceFeign extends MemberService {

}
