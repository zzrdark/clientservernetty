package com.zkja.clientservernetty.nio.http.decoder;

import com.zkja.clientservernetty.common.GsonUtil;
import com.zkja.clientservernetty.domain.TcpReq;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

/**
 * @author zzr
 */
public class HttpStringDecoder extends MessageToMessageDecoder<FullHttpRequest> {
    Logger logger = LoggerFactory.getLogger(HttpStringDecoder.class);
    private final static Charset charset = Charset.forName("UTF-8");
    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest, List<Object> out) throws Exception {
        if (!fullHttpRequest.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
//        HttpJsonRequest request = new HttpJsonRequest(msg, decode0(ctx, msg.content()));
        TcpReq tcpReq = (TcpReq) decode0(ctx,fullHttpRequest.content());
        tcpReq.setHttpRequest(fullHttpRequest);
        out.add(tcpReq);
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception{
        String content = body.toString(charset);

        Object result = beforeRequest(content);
        return result;
    }

    public TcpReq beforeRequest(String content) throws Exception{
        GsonUtil.getEntityFromRequest(content, TcpReq.class);
//        logger.info("request==="+jsonData);
        TcpReq tcpReq = (TcpReq) GsonUtil.getEntityFromRequest(
                content, TcpReq.class);
        return tcpReq;
    }

    /**
     * 测试的话，直接封装，实战中需要更健壮的处理
     */
    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }



}
