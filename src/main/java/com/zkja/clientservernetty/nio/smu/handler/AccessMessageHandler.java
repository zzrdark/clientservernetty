package com.zkja.clientservernetty.nio.smu.handler;

import com.zkja.clientservernetty.domain.TcpReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author zzr
 */
public class AccessMessageHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("AccessMessageHandler: "+msg.toString());
        super.write(ctx, msg, promise);
    }
}
