package com.netty.socket.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


/**
 * 1.服务端
 */
public class NettyServer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public boolean isRunning = false;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workGroup = null;
    private ServerBootstrap bootstrap = null;

    public void start(InetSocketAddress socketAddress) {
        logger.info("Netty服务端启动成功，当前线程名称：" + Thread.currentThread().getName());
        //new 一个主线程组
        bossGroup = new NioEventLoopGroup(5);
        //new 一个工作线程组
        workGroup = new NioEventLoopGroup(200);
        bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer())
                .localAddress(socketAddress)
                //设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        //绑定端口,开始接收进来的连接
        try {
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            logger.info("服务器启动开始监听端口: {}", socketAddress.getPort());
            isRunning = true;
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("服务端执行结束");
            //关闭主线程组
            bossGroup.shutdownGracefully();
            //关闭工作线程组
            workGroup.shutdownGracefully();
            isRunning = false;
        }

    }

    public void stop() {
        if (bossGroup != null && !bossGroup.isShutdown()) {
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null && !workGroup.isShutdown()) {
            workGroup.shutdownGracefully();
        }
        isRunning = false;
        logger.info("关闭服务器");
    }

}






