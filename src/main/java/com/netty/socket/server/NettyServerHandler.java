package com.netty.socket.server;

import cn.hutool.extra.spring.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2.创建ServerHandler
 * 接收消息等
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NettyServerCacheTemplate nettyServerCacheTemplate = SpringUtil.getBean(NettyServerCacheTemplate.class);

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端[" + ctx.channel().id().asLongText() + "]建立链接成功");
        // 存储到map中
        nettyServerCacheTemplate.saveChannel(ctx.channel());
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String name = Thread.currentThread().getName();
        logger.info("服务器收到消息: {}", msg.toString());
        System.out.println("当前线程名称：" + name + "：当前channel的id：" + ctx.channel().id().asLongText());

        ctx.write("当前线程名称：" + name + "  当前channel的id：" + ctx.channel().id().asShortText());
        ctx.flush();
        //接收消息后关闭链接
        ctx.close();
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.info("发生异常");
        // 关闭链接删除对象
        nettyServerCacheTemplate.deleteChannel(ctx.channel());
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        logger.info("链接已关闭" + ctx.channel().id().asLongText());
        // 关闭链接删除对象
        nettyServerCacheTemplate.deleteChannel(ctx.channel());
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端注册" + ctx.channel().id().asLongText());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端取消注册" + ctx.channel().id().asLongText());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端非活跃" + ctx.channel().id().asLongText());
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("读取完成" + ctx.channel().id().asLongText());
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }
}
