package com.netty.socket.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lx
 */
@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NettyClientApplication.class).run(args);
        new Thread(new NettyListener()).start();
        new NettyClient().start();
    }
}
