package com.example.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import java.time.Instant;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private CamelContext camelContext;

    @MessageMapping("/chat")
    public void sendMessage(Message message) {
        logger.info("Received message: {} from {}", message.getText(), message.getSender());
        ProducerTemplate template = camelContext.createProducerTemplate();
        template.sendBodyAndHeader("direct:chat", message.getText(), "sender", message.getSender());
        logger.info("Sent message to direct:chat via ProducerTemplate");
    }

    @JmsListener(destination = "chat.topic.test")
    public void broadcastMessage(String text, @Header("sender") String sender) {
        logger.info("Received from ActiveMQ: {} from {}", text, sender);
        Message message = new Message();
        message.setText(text);
        message.setSender(sender);
        message.setTimestamp(Instant.now().toString());
        messagingTemplate.convertAndSend("/topic/chat", message);
        logger.info("Broadcast to /topic/chat");
    }
}