package com.example.chat;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String text;
    private String timestamp;
    private String sender;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
}