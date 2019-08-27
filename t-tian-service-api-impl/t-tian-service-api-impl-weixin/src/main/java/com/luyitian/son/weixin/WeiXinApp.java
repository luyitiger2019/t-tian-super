package com.luyitian.son.weixin;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2Doc
@EnableSwagger2
@EnableApolloConfig
@EnableFeignClients
public class WeiXinApp {
  public static void main(String[] args) {
      SpringApplication.run(WeiXinApp.class,args);
  }
}
