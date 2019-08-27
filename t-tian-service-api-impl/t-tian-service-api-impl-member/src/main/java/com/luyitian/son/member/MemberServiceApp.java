package com.luyitian.son.member;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2Doc
@EnableSwagger2
@EnableApolloConfig
@MapperScan(basePackages = "com.luyitian.son.member.mapper")
public class MemberServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(MemberServiceApp.class,args);
    }
}
