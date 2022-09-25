package com.netty.socket.controller;

import cn.hutool.json.JSONObject;
import com.netty.socket.client.NettyClientCacheTemplate;
import com.netty.socket.server.NettyServerCacheTemplate;
import com.netty.socket.util.NettyUtil;
import com.netty.socket.util.RSAUtils;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx
 * @date 2022/9/24 19:53
 */
@RestController
@RequestMapping("/netty")
public class NettyController {


    @GetMapping("/sendClientMsg")
    public JSONObject sendClientMsg(String msg, String channelId) throws Exception {
        Channel channel = NettyServerCacheTemplate.channelMap.get(channelId);
        NettyUtil.sendMsg(RSAUtils.encrypt(msg, RSAUtils.getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1HA1n7zCm2khub0CLiJlQHgqPbz52OBldBC9Qy/P1GUozN0PfxfN2BdcUwjzDcTsG2hWfsvtwLjHV7hOX0e2BGTi2mfL+7V8emrF70OTnaQy7oArtJxo0ep7ktY3OYBJmEC7MjEQD4FZMJmmir8S2QnM29zFGWf+pd6KGeQQD7QIDAQAB")), channel);
        return new JSONObject().putOnce("success", "true");
    }


    @GetMapping("/sendServerMsg")
    public JSONObject sendServerMsg(String msg, String channelId) {
        Channel channel = NettyClientCacheTemplate.channelMap.get(channelId);
        NettyUtil.sendMsg(msg, channel);
        return new JSONObject().putOnce("success", "true");
    }
}
