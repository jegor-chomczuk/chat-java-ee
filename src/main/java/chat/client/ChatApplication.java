package chat.client;

import chat.client.util.InputValidator;
import chat.client.util.SystemMessages;

import java.io.IOException;

public class ChatApplication {

    public static void main(String[] args) throws IOException {
        HttpRequest.addUser();

        MessageListener messageListener = new MessageListener(Client.clientName);
        Thread messageListenerThread = new Thread(messageListener);
        messageListenerThread.setDaemon(true);
        messageListenerThread.start();

        while (true) {
            try {
                String input = Client.scanner.nextLine();

                if (input.toLowerCase().startsWith("channel:")) {
                    String commandValue = input.substring(8).replaceAll(" ", "");
                    HttpRequest.switchChannel(Client.clientName, commandValue);
                    Client.setCurrentChannel(commandValue);
                    messageListener.setCurrentChannel(commandValue);

                } else if (input.toLowerCase().startsWith("history:")) {
                    String commandValue = input.substring(8).replaceAll(" ", "");
                    HttpRequest.printHistory(commandValue.toLowerCase(), Client.clientName);

                } else if (input.toLowerCase().startsWith("showmychannels:")) {
                    HttpRequest.showChannels(Client.clientName);

                    // uploadfile:C:\Users\ykhom\Desktop\chatTest.png
                } else if (input.toLowerCase().startsWith("uploadfile:")) {
                    String filePath = input.substring(11).replaceAll(" ", "");
                    HttpRequest.uploadFile(filePath, Client.currentChannel, Client.clientName);

                } else if (input.toLowerCase().startsWith("download:")) {
                    String filePath = input.substring(9).replaceAll(" ", "");
                    HttpRequest.downloadFile(filePath, Client.clientName);

                } else if (input.toLowerCase().startsWith("help:")) {
                    SystemMessages.printCommandsAvailableInChat();

                } else if (input.toLowerCase().startsWith("exit:")) {
                    if (HttpRequest.removeUser(Client.clientName)) {
                        messageListener.stopThread();
                        System.exit(0);
                    }

                } else {
                    if (InputValidator.isInputValid(input)) {
                        HttpRequest.sendMessage(Client.currentChannel, Client.clientName, input, Client.color);
                    }
                }
            } catch (IOException e) {
                break;
            }
        }
    }
}
