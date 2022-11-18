package com.dmos.dmos_client;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DMOSClientContext {
    private Channel parent;

    public void channel(Channel channel){
        this.parent = channel;
    }
    public void send(Object o){
        if(parent != null && parent.isActive()){
            parent.write(new Gson().toJson(o));
            parent.flush();
            log.info("发送消息中");
        }
    }
}
