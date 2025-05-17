package com.example.chat;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ChatRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:chat")
            .log(LoggingLevel.INFO, "Routing message to ActiveMQ: ${body} from ${header.sender}")
            .to("activemq:topic:chat.topic.test");
    }
}