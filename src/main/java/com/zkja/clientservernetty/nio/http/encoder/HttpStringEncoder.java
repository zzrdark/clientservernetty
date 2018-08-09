package com.zkja.clientservernetty.nio.http.encoder;

import com.zkja.clientservernetty.domain.TcpRes;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author zzr
 */
public class HttpStringEncoder extends MessageToMessageEncoder<TcpRes> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TcpRes tcpRes, List<Object> list) throws Exception {

    }
}
