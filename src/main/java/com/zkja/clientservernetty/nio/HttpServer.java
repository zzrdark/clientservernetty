package com.zkja.clientservernetty.nio;

/**
 * @author zzr
 */
import com.zkja.clientservernetty.nio.http.decoder.HttpStringDecoder;
import com.zkja.clientservernetty.nio.http.encoder.HttpStringEncoder;
import com.zkja.clientservernetty.nio.http.handler.SendSmuHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

// 测试coder 和 handler 的混合使用
public class HttpServer {
    final static int httpServerPort = 8000;
    public static void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast(new HttpResponseEncoder());
                            channelPipeline.addLast(new HttpRequestDecoder());

                            //将多个消息转换为单一的FullHttpRequest或FullHttpResponse对象
                            channelPipeline.addLast(new HttpObjectAggregator(65536));

                            channelPipeline.addLast(new HttpStringEncoder());
                            channelPipeline.addLast(new HttpStringDecoder());

                            channelPipeline.addLast(new SendSmuHandler());

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(httpServerPort).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}