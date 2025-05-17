package com.example.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import jakarta.jms.Session;

@Component
public class JmsMessageConverter implements org.springframework.jms.support.converter.MessageConverter {
    private static final Logger logger = LoggerFactory.getLogger(JmsMessageConverter.class);

    @Override
    public Message toMessage(Object object, Session session) throws JMSException {
        logger.info("Converting to JMS message: {}", object);
        if (object instanceof String) {
            return session.createTextMessage((String) object);
        }
        throw new JMSException("Object is not a String");
    }

    @Override
    public Object fromMessage(Message message) throws JMSException {
        logger.info("Converting JMS message: {}", message);
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                logger.info("Converted to: {}", text);
                return text;
            }
            throw new JMSException("Message is not a TextMessage");
        } catch (Exception e) {
            logger.error("Failed to convert JMS message", e);
            throw new JMSException("Failed to convert message: " + e.getMessage());
        }
    }
}