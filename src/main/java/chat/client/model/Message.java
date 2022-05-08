package chat.client.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String channel;
    private String sender;
    private String message;
    private String color;

    public Message(String channel, String sender, String message, String color) {
        this.channel = channel.substring(0, 1).toUpperCase() + channel.substring(1).toLowerCase();
        this.sender = sender;
        this.message = message;
        this.color = color;
    }

    public Message() {
    }

    public String getColor() {
        return color;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String toJSON() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return "[" +
                channel +
                "], [" +
                sender +
                "], [" +
                dtf.format(now) +
                "]: " +
                message;
    }
}
