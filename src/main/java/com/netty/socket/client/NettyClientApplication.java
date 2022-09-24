package com.netty.socket.client;

import com.netty.socket.server.NettyServerApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lx
 */
@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NettyServerApplication.class).run(args);
        new NettyClient().start();
    }
}
