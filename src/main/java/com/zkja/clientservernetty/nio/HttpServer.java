package com.zkja.clientservernetty.nio;

/**
 * @author zzr
 */
import com.zkja.clientservernetty.nio.http.decoder.HttpStringDecoder;
import com.zkja.clientservernetty.nio.http.encoder.HttpStringEncoder;
import com.zkja.clientservernetty.nio.http.handler.LastWriteHandler;
import com.zkja.clientservernetty.nio.http.handler.SendSmuHandler;
import com.zkja.clientservernetty.property.ServerSocketProperties;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

// 测试coder 和 handler 的混合使用
@Component
@EnableConfigurationProperties(ServerSocketProperties.class)
public class HttpServer implements Runnable {

    @Autowired
    private ServerSocketProperties serverSocketProperties;

    private final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            InternalLoggerFactory.setDefaultFactory(new Slf4JLoggerFactory());
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    // 发送缓冲器
                    .option(ChannelOption.SO_SNDBUF, 1024)
                    // 接收缓冲器
                    .option(ChannelOption.SO_RCVBUF, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline channelPipeline = ch.pipeline();
                            channelPipeline.addLast(new LastWriteHandler());
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

            ChannelFuture f = b.bind(Integer.valueOf(serverSocketProperties.getServerport())).sync();
            logger.info("绑定了"+serverSocketProperties.getServerport());
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}