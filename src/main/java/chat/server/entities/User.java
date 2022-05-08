package chat.server.entities;

import chat.server.util.Color;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private final String name;
    private Set<String> channels;
    private String currentChannel;
    public static String color;

    public User(String name) {
        this.name = name;
        this.channels = new HashSet<>();
        this.channels.add("Main");
        this.currentChannel = "Main";
        this.color = Color.randomColor(Color.class);
    }

    public String getName() {
        return name;
    }

    public String getCurrentChannel() {
        return currentChannel.substring(0, 1).toUpperCase() + currentChannel.substring(1).toLowerCase();
    }

    public Set<String> getChannels() {
        return channels;
    }

    public static String getColor() {
        return color;
    }

    public void setCurrentChannel(String newCurrentChannel) {
        if (!currentChannel.equalsIgnoreCase(newCurrentChannel)) {

            String styledName = newCurrentChannel
                    .replaceAll(" ", "")
                    .substring(0, 1)
                    .toUpperCase()
                    + newCurrentChannel
                    .replaceAll(" ", "")
                    .substring(1)
                    .toLowerCase();
            this.currentChannel = styledName;

            if (!channels.contains(styledName)) {
                addChannel(styledName);
            }
        }
    }

    public void addChannel(String channel) {
        channels.add(channel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", channels=" + channels +
                ", currentChannel='" + currentChannel + '\'' +
                '}';
    }
}