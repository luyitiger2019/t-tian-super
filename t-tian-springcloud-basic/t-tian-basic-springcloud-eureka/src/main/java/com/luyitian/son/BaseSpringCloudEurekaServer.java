package com.luyitian.son;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class BaseSpringCloudEurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(BaseSpringCloudEurekaServer.class,args);
    }
}
