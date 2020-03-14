package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
@Slf4j
public class WebSocket {
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("一个新的链接，现在连接数：{}", webSocketSet.size());
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("断开一个链接，现在连接数：{}", webSocketSet.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("收到消息:{}", message);
    }
    @OnError
    public void onError(Session session, Throwable error){
        log.info("发生错误");
        error.printStackTrace();
    }
     public void sendMessage(String message){
         for (WebSocket websocket:
             webSocketSet ) {
             try {
                 websocket.session.getBasicRemote().sendText(message);
             } catch (IOException e) {
                 e.printStackTrace();
                 log.error("【websocket广播消息】I/O发生错误");
             }
         }
     }
}
