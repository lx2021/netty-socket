package com.netty.socket.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lx
 */
@SpringBootApplication
public class NettyServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NettyServerApplication.class).run(args);
        NettyPoolServer.getSingle().run();
    }
}
