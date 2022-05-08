package chat.client;

import chat.server.util.Color;

import java.util.Scanner;

public class Client {
    public static Scanner scanner = new Scanner(System.in, "UTF-8");
    public static String clientName;
    public static String currentChannel;
    public static String color = Color.randomColor(Color.class);

    public Client(String clientName) {
        this.clientName = clientName;
        currentChannel = "Main";
    }

    public static void setCurrentChannel(String channelName) {
        currentChannel = channelName;
    }
}
