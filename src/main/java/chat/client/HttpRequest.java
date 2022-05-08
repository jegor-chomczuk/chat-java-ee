package chat.client;

import chat.client.model.Message;
import chat.client.util.Constants;
import chat.client.util.SystemMessages;
import chat.client.util.InputValidator;
import chat.server.entities.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class HttpRequest {

    public static void addUser() throws IOException {
        SystemMessages.printLogo();
        SystemMessages.printInstruction();

        String userName;
        int responseStatus = 0;

        while (responseStatus != 200) {
            userName = Client.scanner.nextLine();

            if (InputValidator.isInputValid(userName)) {
                java.net.URL url = new URL(Constants.URL + "/add-user");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setRequestProperty("username", userName);
                conn.connect();

                if (conn.getResponseCode() == 200) {
                    responseStatus = 200;
                    new Client(userName);

                    SystemMessages.printHelpInfo();
                    SystemMessages.printWelcomeMessage();

                } else {
                    SystemMessages.printTakenUsernameMessage(userName);
                }
            }
        }
    }

    public static void printHistory(String channelName, String clientName) throws IOException {
        java.net.URL url = new URL(Constants.URL + "/history");

        String styledChannelName = channelName.substring(0, 1).toUpperCase() + channelName.substring(1).toLowerCase();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("channel", styledChannelName);
        conn.setRequestProperty("client", clientName);

        if (conn.getResponseCode() == 200) {
            SystemMessages.printHistoryChannelAvailable(styledChannelName);

            String strCurrentLine;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((strCurrentLine = bufferedReader.readLine()) != null) {
                System.out.println(Constants.SYSTEM_INFO_COLOR + strCurrentLine);
            }

            System.out.println(Constants.COLOR_RESET);
            bufferedReader.close();

        } else {
            SystemMessages.printHistoryChannelUnavailable(channelName);
        }
        conn.connect();
    }

    public static void switchChannel(String clientName, String channel) throws IOException {
        java.net.URL url = new URL(Constants.URL + "/switch-channel");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("username", clientName);
        conn.setRequestProperty("channel", channel.substring(0, 1).toUpperCase() + channel.substring(1).toLowerCase());
        conn.connect();

        SystemMessages.printNewChannelWelcomeMessage(channel);

        conn.getHeaderField("channel");
    }

    public static void showChannels(String clientName) throws IOException {
        java.net.URL url = new URL(Constants.URL + "/channels");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("username", clientName);
        conn.connect();

        if (conn.getResponseCode() == 200) {

            SystemMessages.printChannelsAvailable();

            String strCurrentLine;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((strCurrentLine = bufferedReader.readLine()) != null) {
                System.out.println(strCurrentLine);
            }

            System.out.println();
            bufferedReader.close();
        }
    }

    public static void sendMessage(String currentChannel, String clientName, String text, String color) throws IOException {
        String backgroundColor = color;
        if (backgroundColor == null) {
            backgroundColor = "";
        }

        Message message = new Message(currentChannel, clientName, text, backgroundColor);

        java.net.URL url = new URL(Constants.URL + "/send-message");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("channel", currentChannel.substring(0, 1).toUpperCase() + currentChannel.substring(1).toLowerCase());
        conn.setRequestProperty("sender", clientName);
        conn.setDoOutput(true);

        OutputStream outputStream = conn.getOutputStream();

        try {
            String json = message.toJSON();
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            conn.getResponseCode();
        } finally {
            outputStream.close();
        }
    }

    public static void uploadFile(String filePath, String currentChannel, String clientName) throws IOException {
        File uploadFile = new File(filePath);
        System.out.println(Constants.SYSTEM_MESSAGE_COLOR + "File to upload: " + Constants.COLOR_RESET + filePath);

        URL url = new URL(Constants.URL + "/file");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("fileName", uploadFile.getName());
        conn.setRequestProperty("channel", currentChannel);
        conn.setRequestProperty("sender", clientName);

        OutputStream outputStream = conn.getOutputStream();

        FileInputStream inputStream = new FileInputStream(uploadFile);

        byte[] buffer = new byte[1024];
        int bytesRead = -1;

        System.out.println(Constants.SYSTEM_MESSAGE_COLOR + "Start uploading data to server...");

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        System.out.println("Data uploaded");
        outputStream.close();
        inputStream.close();

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Upload status: " + Constants.COLOR_RESET + "DONE\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String fileNewPath = reader.readLine();
            String message = "Sends file to download " + fileNewPath;
            sendMessage(currentChannel, clientName, message, User.color);


        } else {
            System.out.println("Upload status: " + Constants.COLOR_RESET + "INTERRUPTED");
        }
    }

    public static void downloadFile(String filePath, String clientName) throws IOException {
        File uploadFile = new File(filePath);

        URL url = new URL(Constants.URL + "/file");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("filePath", filePath);
        conn.setRequestProperty("client", clientName);
        conn.setRequestProperty("fileName", uploadFile.getName());


        System.out.println(Constants.SYSTEM_MESSAGE_COLOR + "File to download: " + Constants.COLOR_RESET + filePath);
        System.out.println(Constants.SYSTEM_MESSAGE_COLOR + "Start downloading data from server...");
        System.out.println("Data downloaded");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Download status: " + Constants.COLOR_RESET + "DONE\n");

        } else {
            System.out.println("Download status: " + Constants.COLOR_RESET + "INTERRUPTED");
        }
    }

    public static boolean removeUser(String clientName) throws IOException {
        java.net.URL url = new URL(Constants.URL + "/remove-user");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("username", clientName);
        conn.connect();

        SystemMessages.goodbyeMessage();

        return (conn.getHeaderField("user").equals("removed"));
    }
}
