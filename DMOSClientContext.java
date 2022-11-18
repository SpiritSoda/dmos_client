package com.dmos.dmos_client;

import io.netty.channel.Channel;

public class DMOSClientContext {
    private Channel parent;

    public void channel(Channel channel){
        this.parent = channel;
    }
    public void send(Object o){
        if(parent != null)
            parent.writeAndFlush(o);
    }
}
