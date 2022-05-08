package chat.server.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import chat.server.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    private final String channel;
    private final String sender;
    private String message;
    private final String color;


    public Message(String channel, String sender, String message) {
        this.channel = channel.substring(0, 1).toUpperCase() + channel.substring(1).toLowerCase();
        this.sender = sender;
        this.message = message;
        this.color = getColorBySender(sender);
    }

    public String getChannel() {
        return channel;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getColor() {
        return color;
    }

    public static Message fromJSON(String message) {
        Gson builder = new GsonBuilder().create();
        return builder.fromJson(message, Message.class);
    }

    public static String getColorBySender(String sender) {
        UserRepository userRepository = UserRepository.getInstance();
        return userRepository.getUser(sender).getColor();
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
