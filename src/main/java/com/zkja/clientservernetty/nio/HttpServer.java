package com.zkja.clientservernetty.nio;

/**
 * @author zzr
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

// 测试coder 和 handler 的混合使用
public class HttpServer {
    final static int httpServerPort = 8000;
    public static void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 都属于ChannelOutboundHandler，逆序执行
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            /*ch.pipeline().addLast(new StringEncoder());*/

                            // 都属于ChannelIntboundHandler，按照顺序执行
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            /*ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new BusinessHandler());*/
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