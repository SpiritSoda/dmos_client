package com.dmos.dmos_client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class DMOSClient {
    private final InetSocketAddress socketAddress;
    private final ChannelInboundHandlerAdapter handlerAdapter;
    public DMOSClient(InetSocketAddress socketAddress, ChannelInboundHandlerAdapter handlerAdapter){
        this.socketAddress = socketAddress;
        this.handlerAdapter = handlerAdapter;
    }
    public void connect(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new DMOSClientChannelInitializer(handlerAdapter));

        try {
            ChannelFuture future = bootstrap.connect(socketAddress).sync();
            log.info("客户端成功连接....");
            // 设置attr
            future.channel().attr(AttributeKey.valueOf("key")).set("sssss");
            //发送消息
            //future.channel().writeAndFlush(sendMsg);
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("无法连接父服务器");
        } finally {
            group.shutdownGracefully();
        }
    }
}
