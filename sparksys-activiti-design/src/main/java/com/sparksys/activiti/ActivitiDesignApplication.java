package com.sparksys.activiti;

import com.sparksys.boot.SparkBootApplication;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: activiti工作流引擎启动类
 *
 * @author: zhouxinlei
 * @date: 2020-07-16 20:01:10
 */
@SpringBootApplication(scanBasePackages = {"com.sparksys.activiti"}, exclude = SecurityAutoConfiguration.class)
public class ActivitiDesignApplication extends SparkBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiDesignApplication.class, args);
    }

}
