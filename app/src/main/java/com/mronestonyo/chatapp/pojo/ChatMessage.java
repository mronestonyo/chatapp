package com.mronestonyo.chatapp.pojo;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ChatMessage {
    private String Message;
    private String Username;

    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(ChatMessage.class)
    }

    public ChatMessage(String message, String username) {
        Message = message;
        Username = username;
    }

    public String getMessage() {
        return Message;
    }

    public String getUsername() {
        return Username;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Username", Username);
        result.put("Message", Message);

        return result;
    }
}
