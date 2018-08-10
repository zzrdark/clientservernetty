package com.zkja.clientservernetty.nio.http.encoder;

import com.zkja.clientservernetty.common.GsonUtil;
import com.zkja.clientservernetty.domain.TcpReq;
import com.zkja.clientservernetty.domain.TcpRes;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

/**
 * @author zzr
 */
public class HttpStringEncoder extends MessageToMessageEncoder<TcpRes> {
    private final Logger logger = LoggerFactory.getLogger(HttpStringEncoder.class);
    final static Charset charset = Charset.forName("utf-8");


    protected ByteBuf encode0(ChannelHandlerContext ctx, TcpRes tcpRes) {
        String respJson = GsonUtil.getJson(tcpRes,tcpRes.getBwlsh());
        ByteBuf encodeBuf = Unpooled.copiedBuffer(respJson, charset);
        return encodeBuf;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, TcpRes tcpRes, List<Object> out) throws Exception {
        //编码
        logger.info("HttpStringEncoder");
        ByteBuf body = encode0(ctx, tcpRes);
        HttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, body);
        response.headers().set(CONTENT_TYPE, "text/json");
        HttpUtil.setContentLength(response, body.readableBytes());
        out.add(response);
    }
}
