package com.love.wife.service;

import com.love.wife.model.websocket.ImageEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/9 23:10
 * <p>
 * websocket服务
 */
@Slf4j
@Component
@ServerEndpoint(value = "/webSocketService", encoders = {ImageEncoder.class})
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数，应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接回话，需要通过它来给客户端发送数据。
     */
    private Session session;
    /**
     * 接收userId
     */
    private String userId = "";

}
