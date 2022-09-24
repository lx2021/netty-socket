package com.netty.socket.util;

import io.netty.channel.Channel;

/**
 * @author lx
 * @date 2022/9/24 19:59
 */
public class NettyUtil {



    /**
     * 想指定链接发送数据
     *
     * @param msg     消息
     * @param channel 指定链接
     */
    public static String sendMsg(String msg, Channel channel) {
        try {
            if (channel.isActive()) {
                channel.write(msg);
                channel.flush();
                return "success";
            } else {
                return "不在线";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
