package com.netty.socket.client;

import cn.hutool.extra.spring.SpringUtil;

import java.util.List;

/**
 * @author lx
 * @date 2022/9/24 17:32
 */
public class NettyListener implements Runnable {

    private final NettyClientCacheTemplate nettyClientCacheTemplate = SpringUtil.getBean(NettyClientCacheTemplate.class);


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                final List<String> online = nettyClientCacheTemplate.getOnline();
                System.out.println("活跃用户为:" + online);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
