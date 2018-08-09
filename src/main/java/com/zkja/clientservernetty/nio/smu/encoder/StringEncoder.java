package com.zkja.clientservernetty.nio.smu.encoder;

import com.zkja.clientservernetty.common.TcpFormatUtils;
import com.zkja.clientservernetty.domain.TcpReq;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author zzr
 */
public class StringEncoder extends MessageToByteEncoder<TcpReq> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TcpReq tcpReq, ByteBuf byteBuf) throws Exception {

        if (tcpReq != null) {
            String str = TcpFormatUtils.getReq(tcpReq);
            System.out.println("StringEncoder: "+str);
            byteBuf.writeBytes(str.getBytes());
        }
    }
}
