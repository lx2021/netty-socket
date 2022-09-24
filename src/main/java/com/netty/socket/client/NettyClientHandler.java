package com.netty.socket.client;

import cn.hutool.extra.spring.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2.客户端处理器
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NettyClientCacheTemplate nettyClientCacheTemplate = SpringUtil.getBean(NettyClientCacheTemplate.class);

    // 出发链接
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端与服务端链接成功");
        // 将channel 保存到 map中
        nettyClientCacheTemplate.saveChannel(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object key = ctx.channel().attr(AttributeKey.valueOf("key")).get();
        System.out.println("获取attr中的值：" + key);
        logger.info("客户端收到消息: {}", msg.toString());
        logger.info("当前channel的编号是" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nettyClientCacheTemplate.deleteChannel(ctx.channel());
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        nettyClientCacheTemplate.deleteChannel(ctx.channel());
    }
}
