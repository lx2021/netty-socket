package com.netty.socket;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lx
 * @date 2022/9/24 15:51
 */
@SpringBootApplication
public class Application {

    static {
        System.getProperties().setProperty("spring.config.location", "classpath:netty/application.properties");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }
}
