package com.dmos.dmos_client;

import com.dmos.dmos_common.util.ParseUtil;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DMOSClientContext {
    private int id;
    // 连接token，不是机器token
    // 每个机器有自己的token，连接前向register发送token(http请求)，register会返回一个限时的连接token用于向非register机器连接
    // 从而避免机器自己的token泄漏到非register机器
    private String token;
    private Channel parent;

    public void channel(Channel channel){
        this.parent = channel;
    }
    public void id(int id) { this.id = id; }
    public void token(String token) { this.token = token; }
    public void send(Object o){
        if(parent != null && parent.isActive()){
            parent.writeAndFlush(ParseUtil.encode(o, false) + "\r");
//            parent.flush();
        }
    }
    public void sendWithoutFlush(Object o){
        if(parent != null && parent.isActive()){
            parent.write(ParseUtil.encode(o, false) + "\r");
//            parent.flush();
        }
    }
}
