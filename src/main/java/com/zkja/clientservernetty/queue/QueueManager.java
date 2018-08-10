package com.zkja.clientservernetty.queue;


import com.zkja.clientservernetty.domain.TcpRes;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @authon zzr
 */

public class QueueManager {
    private static LinkedBlockingQueue<TcpRes> sendSmcQueue = new LinkedBlockingQueue<TcpRes>();
    //管理下发流水号
    private static  Map<String,ChannelHandlerContext> bwlshMap = new ConcurrentHashMap<String,ChannelHandlerContext>();

    //管理设备channel
    private  static Map<String, ChannelHandlerContext> map = new ConcurrentHashMap<String, ChannelHandlerContext>();

    public static AtomicInteger count = new AtomicInteger();

    Logger logger = Logger.getLogger(QueueManager.class);

    public static ChannelHandlerContext getBwlshMap(String key){
        return bwlshMap.get(key);
    }

    public static void putBwlshMap(String key, ChannelHandlerContext ctx){
        bwlshMap.put(key,ctx);
    }
    public static ChannelHandlerContext rmBwlshMap(String key){
       ChannelHandlerContext ctx = bwlshMap.remove(key);
       return ctx;
    }

    public static ChannelHandlerContext getMap(String key){
        return map.get(key);
    }

    public static void putMap(String key, ChannelHandlerContext ctx){
        map.put(key,ctx);
    }

    public boolean addSmcQueue(TcpRes tcpRes){
        logger.debug("sendSmcQueue: "+ tcpRes);
        return sendSmcQueue.offer(tcpRes);
    }

    public TcpRes pollSmcQueue(){
        TcpRes tcpRes = sendSmcQueue.poll();
        if (tcpRes==null){
            return null;
        }
        if(System.currentTimeMillis()-tcpRes.getTime()>10000){
        	logger.info("超时设备:"+tcpRes.getImei());
            return pollSmcQueue();
        }
        count.incrementAndGet();
        logger.info("smc队列大小："+getSmcSize()+"消费了："+count);
        return tcpRes;
    }

    public int getSmcSize(){
    	return sendSmcQueue.size();
    }
}
