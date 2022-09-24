package com.netty.socket.server;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 4.保存与客户端链接的channel
 */
@Component
public class NettyServerCacheTemplate {
    // 记录当前在线channel数量
    public static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * 存储对应的用户名与Netty链接实例
     */
    public void saveChannel(Channel channel) {
        channelMap.put(channel.id().asLongText(), channel);
    }

    /**
     * 获取存储池中的链接实例
     */
    public Object getChannel(String name) {
        return channelMap.get(name);
    }

    /**
     * 删除存储池实例
     */
    public void deleteChannel(Channel channel) {
        channelMap.remove(channel.id().asLongText());
    }

    /**
     * 获取储存池链接数
     */
    public Integer getSize() {
        return channelMap.size();
    }

    /**
     * 返回在线用户列表信息
     */
    public List<String> getOnline() {
        return new ArrayList<>(channelMap.keySet());
    }


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
