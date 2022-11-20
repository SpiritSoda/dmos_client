package com.dmos.dmos_client;

import com.google.gson.Gson;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DMOSClientContext {
    private int id;
    private Channel parent;

    public void channel(Channel channel){
        this.parent = channel;
    }
    public void id(int id) { this.id = id; }
    public void send(Object o){
        if(parent != null && parent.isActive()){
            parent.writeAndFlush(new Gson().toJson(o));
        }
    }
}
