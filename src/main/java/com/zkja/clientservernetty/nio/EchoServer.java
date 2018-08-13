/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.zkja.clientservernetty.nio;


import com.zkja.clientservernetty.common.SmuConstant;
import com.zkja.clientservernetty.common.TcpFormatUtils;
import com.zkja.clientservernetty.nio.smu.decoder.StringDecoder;
import com.zkja.clientservernetty.nio.smu.encoder.StringEncoder;
import com.zkja.clientservernetty.nio.smu.handler.AccessMessageHandler;
import com.zkja.clientservernetty.nio.smu.handler.EchoServerHandler;
import com.zkja.clientservernetty.nio.smu.handler.HttpHandler;
import com.zkja.clientservernetty.property.ServerSocketProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Echoes back any received data from a client.
 */
@Component
@EnableConfigurationProperties(ServerSocketProperties.class)
public final class EchoServer implements Runnable {


    private static Logger logger = LoggerFactory.getLogger(TcpFormatUtils.class);
    @Autowired
    private ServerSocketProperties serverSocketProperties;

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));



    @Override
    public void run()  {

        // Configure SSL.
        /*final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }*/

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        //使用分隔符截取报文
        ByteBuf byteBuf = Unpooled.copiedBuffer("]".getBytes());
        /*DelimiterBasedFrameDecoder delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(1024,byteBuf);*/

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             /*.handler(new LoggingHandler(LogLevel.INFO))*/
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     //解析zkja协议
                     final StringDecoder stringDecoder = new StringDecoder();
                     final StringEncoder stringEncoder = new StringEncoder();

                     final HttpHandler httpHandler = new HttpHandler(serverSocketProperties.getSmcUrl()+"?port="+serverSocketProperties.getPort());
                     //handler
                     final EchoServerHandler serverHandler = new EchoServerHandler();
                     final AccessMessageHandler accessMessageHandler = new AccessMessageHandler();

                     final DelimiterBasedFrameDecoder delimiterBasedFrameDecoder = new DelimiterBasedFrameDecoder(1024,byteBuf);
                     /*ch.pipeline().addLast(new ReadTimeoutHandler(10));
                     ch.pipeline().addLast(new WriteTimeoutHandler(1));*/

                     p.addLast(new LoggingHandler(LogLevel.INFO));

                     p.addLast(serverHandler);
                     p.addLast(delimiterBasedFrameDecoder);

                     p.addLast(stringDecoder);
                     p.addLast(stringEncoder);

                     p.addLast(accessMessageHandler);
                     p.addLast(httpHandler);
                 }
             });
             logger.info("EchoServer注册完成");
            // Start the server.
            ChannelFuture f = b.bind(Integer.valueOf(serverSocketProperties.getPort())).sync();/*.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        System.out.println("serverSocketChannel  监听了"+serverSocketProperties.getPort()+"端口");
                        logger.info("serverSocketChannel  监听了"+serverSocketProperties.getPort()+"端口");
                    }

                }
            });*/

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
