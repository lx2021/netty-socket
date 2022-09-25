package com.netty.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 1.客户端
 **/
public class NettyClient {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());

        connect(bootstrap);
        group.shutdownGracefully();
    }

    /**
     * 连接服务端
     *
     * @param bootstrap
     * @throws InterruptedException
     */
    public void connect(Bootstrap bootstrap) {
        try {

            ChannelFuture future = bootstrap.connect("127.0.0.1", 8091).sync();
            logger.info("客户端成功....");
            // 设置attr
            future.channel().attr(AttributeKey.valueOf("name")).set("koi");
            //发送消息
            //future.channel().writeAndFlush("connect");
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("连接服务端失败，正在重试");
            connect(bootstrap);
        }
    }
}





