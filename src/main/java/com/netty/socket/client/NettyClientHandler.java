package com.netty.socket.client;

import cn.hutool.extra.spring.SpringUtil;
import com.netty.socket.util.RSAUtils;
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
        Object key = ctx.channel().attr(AttributeKey.valueOf("name")).get();
        System.out.println("获取attr中的值：" + key);
        logger.info("客户端收到消息: {}", RSAUtils.decrypt(msg.toString(),RSAUtils.getPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALUcDWfvMKbaSG5vQIuImVAeCo9vPnY4GV0EL1DL8/UZSjM3Q9/F83YF1xTCPMNxOwbaFZ+y+3AuMdXuE5fR7YEZOLaZ8v7tXx6asXvQ5OdpDLugCu0nGjR6nuS1jc5gEmYQLsyMRAPgVkwmaaKvxLZCczb3MUZZ/6l3ooZ5BAPtAgMBAAECgYEAh8FoVyEXIer7YjRoeG9DJKfwGIY1lt4EPRIW+YR38KAtW2iwqvsxvKUyDLBwZbvpmAA/Nz8tdHBcENTtwN6uHNSnmeXxmaoahMSq53dF5+61rqlAQhrwRtjOb6tesc98i3QzhoX10ehFe1fz00K5QDXP6GxsH9YnEDi3xH8pAGECQQDmQOSxceAcz4ECMN7ZjDy4dtp7/ns+M9A06qsGKw0pSaz9x8fFwHVIBmlLyhV1+IzGLQ6YSqxF7gids4y30MN1AkEAyVxkpb8RYbvJT7wmfnMreeAIaFN8winEsyc9OXmwMtEGUZqZutAe3yVUDKEGe3NhFq0L28jENEpK/NE6nmsHmQJAXpme3EC7IVsn1+yYQq1ZbTh3v6XooL/M9VDM/3XErIf2qTXhoB/Yj8UCkf7vHk8GF43/hxZ2/Sw1IgXY9NSy2QJBAMCZJBe5GsoNkO84OuWGCVaoZ8JJ4LHiu6bUaGY6M8NXVSDmrBYYjP7JOIxf1NBturayy/3sGWcejH0nL1dpbjkCQGCNLQymTvTFXIqgVNRfl0nTfjimgg1L9lSR3Feqo6ww1/HN4deBt0vqGiuoq8yq89mBZEtTYNQJ3FXcSKRxltc=")));
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
