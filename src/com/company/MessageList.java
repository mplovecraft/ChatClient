package com.company;

import com.company.ChatRooms.ChatRoom;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageList implements Serializable{
    private ArrayList<Message> messagesList;
    static final long serialVersionUID = 30;

    public MessageList() {
        messagesList = new ArrayList<>();
    }

    public ArrayList<Message> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(Message message) {
        this.messagesList.add(message);
    }
}


