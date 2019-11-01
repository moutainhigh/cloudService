package com.cold.mtservice;

import com.cold.mtservice.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@EnableEurekaClient
@Order(0)
public class MtserviceApplication {

    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication(MtserviceApplication.class);
        //sa.addListeners(new MainBusiListeners());
        //SpringContextUtil.setApplicationContext(context);
        ApplicationContext context = sa.run(args);
        System.out.println(context);
    }

}
