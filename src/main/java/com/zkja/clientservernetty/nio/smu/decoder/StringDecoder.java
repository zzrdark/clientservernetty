package com.zkja.clientservernetty.nio.smu.decoder;


import com.zkja.clientservernetty.common.TcpFormatUtils;
import com.zkja.clientservernetty.domain.TcpRes;
import com.zkja.clientservernetty.nio.smu.handler.HttpHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 用来解析zkja的协议
 * @author zzr
 */
public class StringDecoder extends ByteToMessageDecoder {

    final Logger logger = LoggerFactory.getLogger(StringDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println(byteBuf.toString());
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String tcpStr = new String (bytes);

        logger.info("StringDecoder"+tcpStr);
        System.out.println("StringDecoder: "+tcpStr);
        TcpRes tcpRes = TcpFormatUtils.getRes(tcpStr+"]");
        list.add(tcpRes);
    }
}
