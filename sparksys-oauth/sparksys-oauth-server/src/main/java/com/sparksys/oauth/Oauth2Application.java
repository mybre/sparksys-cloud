package com.sparksys.oauth;

import com.sparksys.boot.SparkBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: 授权认证启动类
 *
 * @author zhouxinlei
 * @date 2020-05-24 12:21:13
 */
@SpringBootApplication(scanBasePackages = {"com.sparksys.oauth"})
public class Oauth2Application extends SparkBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }
}
