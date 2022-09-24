package com.netty.socket.server;

import com.netty.socket.server.threadpool.ThreadPoolTaskConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component
public class NettyPoolServer {
    @Resource
    private ThreadPoolTaskConfig poolTaskExecutor;
    private static NettyPoolServer single = null;

    private NettyServer nettyServer;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        single = this;
        single.poolTaskExecutor = this.poolTaskExecutor;
        // 初使化时将已静态化的testService实例化
    }

    public static NettyPoolServer getSingle() {
        return single;
    }

    public void run() {
        poolTaskExecutor.taskExecutor().execute(() -> {
            //启动服务端
            System.out.println("NettyPoolServer当前线程池：" + Thread.currentThread().getName());
            nettyServer = new NettyServer();
            nettyServer.start(new InetSocketAddress("127.0.0.1", 8091));
        });
    }

    public void stop() {
        if (nettyServer != null && nettyServer.isRunning) {
            nettyServer.stop();
        }
    }

    public boolean getIsRunning() {
        if (nettyServer == null) {
            return false;
        }
        return nettyServer.isRunning;
    }

}
