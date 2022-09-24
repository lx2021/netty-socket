package com.netty.socket.controller;

import cn.hutool.json.JSONObject;
import com.netty.socket.client.NettyClientCacheTemplate;
import com.netty.socket.server.NettyServerCacheTemplate;
import com.netty.socket.util.NettyUtil;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx
 * @date 2022/9/24 19:53
 */
@RequestMapping("/netty")
@RestController
public class NettyController {


    @GetMapping("/sendClientMsg")
    public JSONObject sendClientMsg(String msg, String channelId) {
        Channel channel = NettyServerCacheTemplate.channelMap.get(channelId);
        NettyUtil.sendMsg(msg, channel);
        return new JSONObject().putOnce("success", "true");
    }


    @GetMapping("/sendServerMsg")
    public JSONObject sendServerMsg(String msg, String channelId) {
        Channel channel = NettyClientCacheTemplate.channelMap.get(channelId);
        NettyUtil.sendMsg(msg, channel);
        return new JSONObject().putOnce("success", "true");
    }
}
