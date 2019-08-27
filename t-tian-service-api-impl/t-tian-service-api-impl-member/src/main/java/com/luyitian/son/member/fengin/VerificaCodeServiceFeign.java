package com.luyitian.son.member.fengin;

import com.luyitian.son.weixin.service.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("t-tian-weixin")
public interface VerificaCodeServiceFeign  extends VerificaCodeService {
}
