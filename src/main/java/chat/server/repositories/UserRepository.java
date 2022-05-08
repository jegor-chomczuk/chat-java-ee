package chat.server.repositories;

import chat.server.entities.User;

import java.util.*;

public class UserRepository {
    private static final UserRepository USER_REPOSITORY = new UserRepository();
    private static Map<String, User> userMap = new HashMap<>();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        return USER_REPOSITORY;
    }

    public synchronized void add(User user) {
        userMap.put(user.getName(), user);
    }

    public static synchronized User getUser(String name) {
        if (userMap.containsKey(name))
            return userMap.get(name);

        return null;
    }

    public synchronized List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public synchronized void removeUser(String userName) {
        userMap.remove(userName);
    }

    public static boolean isUsernameTaken(String userName) {
        User user = UserRepository.getInstance().getUser(userName);
        return (user != null);
    }

    public synchronized List<String> getAllUsersWithCurrentChannel(String channel) {
        List<String> userNameList = new ArrayList<>();

        for (int i = 0; i < userMap.size(); i++) {
            Object key = userMap.keySet().toArray()[i];
            String userCurrentChannel = userMap.get(key).getCurrentChannel();
            if (userCurrentChannel.equals(channel)) {
                userNameList.add(userMap.get(key).getName());
            }
        }
        return userNameList;
    }
}