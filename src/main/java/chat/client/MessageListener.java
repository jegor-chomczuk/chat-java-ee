package chat.client;

import chat.client.model.Message;
import chat.client.util.Constants;
import chat.client.util.RequestTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MessageListener implements Runnable {
    private static String userName;
    private String currentChannel;
    private boolean isDisabled;

    MessageListener(String userName) {
        MessageListener.userName = userName;
        this.isDisabled = false;
        this.currentChannel = "Main";
    }

    public void setCurrentChannel(String currentChannel) {
        this.currentChannel = currentChannel.substring(0, 1).toUpperCase() + currentChannel.substring(1).toLowerCase();
    }

    @Override
    public void run() {
        try {
            while (!isDisabled) {

                java.net.URL url = new URL(Constants.URL + "/get-messages");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("receiver", userName);
                conn.setRequestProperty("channel", currentChannel);
                conn.setRequestProperty("Connection", "Keep-Alive");

                InputStream inputStream = conn.getInputStream();
                String json = null;
                if (inputStream != null) {
                    byte[] buffer = RequestTransformer.inputStreamToArray(inputStream);
                    json = new String(buffer, StandardCharsets.UTF_8);
                }
                if (json != null && !json.equals("")) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    Message[] messageList = objectMapper.readValue(json, Message[].class);
                    if (messageList != null) {
                        for (Message message : messageList) {
                            System.out.println(message.getColor() + "[" + message.getSender() + "]" + Constants.COLOR_RESET + ": " + message.getMessage());
                        }
                        inputStream.close();
                    }
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public void stopThread() {
        this.isDisabled = true;
    }
}
