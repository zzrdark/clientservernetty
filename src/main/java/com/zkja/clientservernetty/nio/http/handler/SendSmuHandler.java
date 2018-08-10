package com.zkja.clientservernetty.nio.http.handler;


import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpVersion.*;

import com.zkja.clientservernetty.domain.TcpReq;
import com.zkja.clientservernetty.queue.QueueManager;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zzr
 */
public class SendSmuHandler extends SimpleChannelInboundHandler<TcpReq> {

    Logger logger = LoggerFactory.getLogger(SendSmuHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext context, TcpReq tcpReq) throws Exception {
        String imei = tcpReq.getImei();
        if (imei == null) {
            return;
        }
        ChannelHandlerContext smuChannel = QueueManager.getMap(imei);
        if (smuChannel == null) {
            logger.error("该设备不在该服务器上imei :" + tcpReq.getImei());
            return;
        }

        if (QueueManager.getBwlshMap(tcpReq.getImei()) != null) {
            logger.error("报文流水号有冲突，bwlsh " + tcpReq.getBwlsh());
            return;
        }
        //TODO 记录报文流水号
        QueueManager.putBwlshMap(tcpReq.getBwlsh(), context);

        ChannelFuture future = smuChannel.write(tcpReq);

        if (!HttpUtil.isKeepAlive(tcpReq.getHttpRequest())) {
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future future) throws Exception {
                    context.close();
                }
            });
        }


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status);
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.content().writeBytes(Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        ctx.channel().write(response).addListener(ChannelFutureListener.CLOSE);
    }

   /* private FullHttpResponse getHttpResponse(HttpResponse response) {
        HttpResponseStatus statusCode = response.getStatus();
        FullHttpResponse nettyResponse = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.valueOf(statusCode));

        HttpUtil.setContentLength(nettyResponse, response.getContentLength());

        Map<String, String> responseHeaders = response.getHeaders();
        for (Map.Entry<String, String> header : responseHeaders.entrySet()) {
            nettyResponse.headers().add(header.getKey(), header.getValue());
        }
        String responseString = response.getResult();
        if (responseString != null) {
            Charset encoding = Charset.forName(response.getCharacterEncoding());
            nettyResponse.content().writeBytes(Unpooled.copiedBuffer(responseString, encoding));
        }
        return nettyResponse;
    }*/
}
