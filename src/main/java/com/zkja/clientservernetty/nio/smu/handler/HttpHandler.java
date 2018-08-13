package com.zkja.clientservernetty.nio.smu.handler;

import com.zkja.clientservernetty.common.HttpGetPostUtil;
import com.zkja.clientservernetty.common.HttpUtils;
import com.zkja.clientservernetty.domain.TcpReq;
import com.zkja.clientservernetty.domain.TcpRes;
import com.zkja.clientservernetty.nio.EchoServer;
import com.zkja.clientservernetty.queue.QueueManager;
import io.netty.channel.*;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * @author zzr
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    final Logger logger = LoggerFactory.getLogger(HttpHandler.class);
    private  String url;
    public HttpHandler(String url){
        this.url = url;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof TcpRes){
            url += "&sourceIp="+((TcpRes)msg).getSourceIp();
            //TODO 以后这里加判断是否下发上来的响应消息
            ChannelHandlerContext httpChannel = QueueManager.rmBwlshMap(((TcpRes)msg).getBwlsh());
            if(httpChannel != null){
                logger.info("响应smc下发消息");
                httpChannel.write(msg);
                return ;
            }

            logger.info("request="+((TcpRes)msg).toString());
            Map<String, String> map = HttpUtils.getHttpReq((TcpRes) msg);
            String respJson = HttpGetPostUtil.doPostStr(url, map);
            System.out.println("HttpHandler"+respJson);
            TcpReq tcpReq = HttpUtils.getHttpRes(respJson);
            setImeiChannel(tcpReq, ctx);
            ChannelFuture future = ctx.write(tcpReq);
            future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Void>>() {
                @Override
                public void operationComplete(io.netty.util.concurrent.Future<? super Void> future) throws Exception {

                }
            });
        }
    }

    private void setImeiChannel(TcpReq tcpReq, ChannelHandlerContext ctx){
        if(tcpReq != null || tcpReq.getImei() != null ){
            QueueManager.putMap(tcpReq.getImei(),ctx);
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
