package com.zkja.clientservernetty.nio.http.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzr
 */
public class LastWriteHandler extends ChannelOutboundHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(LastWriteHandler.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.info("输出响应");
        ctx.writeAndFlush(msg);
    }
}
