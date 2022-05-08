package chat.server.repositories;

import chat.server.entities.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class MessageRepository {
    private static final MessageRepository MESSAGE_REPOSITORY = new MessageRepository();
    private final Gson builder = new GsonBuilder().create();
    private final Map<String, List<Message>> map = new HashMap<>();

    private UserRepository userRepository = UserRepository.getInstance();

    private MessageRepository() {
    }

    public static MessageRepository getInstance() {
        return MESSAGE_REPOSITORY;
    }

    public synchronized void addMessage(String sender, String channelName, Message message) {
        List<String> userNameList = userRepository.getAllUsersWithCurrentChannel(channelName);
        LinkedList<Message> list;

        for (String name : userNameList) {
            if (!sender.equals(name)) {
                list = (LinkedList<Message>) map.get(name);
                list.add(message);
            }
        }
    }

    public synchronized void addReceiver(String channelName) {
        List<String> userNameList = userRepository.getAllUsersWithCurrentChannel(channelName);
        for (String name : userNameList) {
            if (!map.containsKey(name)) map.put(name, new LinkedList<>());
        }
    }

    public String getMessageListForReceiverToJSON(String receiver, String channel) {
        List<Message> list = null;
        // Makes a list of all messages for indicated user
        if (map.containsKey(receiver)) {
            list = map.get(receiver);
        }

        if (list == null || list.isEmpty()) {
            return "";

        // Eliminate messages from the list where channel != message.getChannel()
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getChannel().equals(channel) && list.get(i).getMessage().equals("") || list.get(i).getMessage() == null) {
                    list.remove(i);
                }
            }
        }
        return builder.toJson(list);
    }

    public void clearMessagesDeliveredToReceiver(String receiver) {
        map.remove(receiver);
    }
}