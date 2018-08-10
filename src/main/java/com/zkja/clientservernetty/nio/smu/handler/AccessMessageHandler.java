package com.zkja.clientservernetty.nio.smu.handler;

import com.zkja.clientservernetty.domain.TcpReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzr
 */
public class AccessMessageHandler extends ChannelOutboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(AccessMessageHandler.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("AccessMessageHandler: "+msg.toString());
        super.write(ctx, msg, promise);
    }
}
