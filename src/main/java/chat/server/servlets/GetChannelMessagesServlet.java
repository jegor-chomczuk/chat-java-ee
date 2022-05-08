package chat.server.servlets;

import chat.client.util.Constants;
import chat.server.repositories.MessageRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/get-messages

HEADER: receiver
VALUE: {userName}
*/

@WebServlet(name = "GetChannelMessageList", urlPatterns = "/get-messages")
public class GetChannelMessagesServlet extends HttpServlet {
    private MessageRepository messageRepository = MessageRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String receiver = request.getHeader("receiver");
        String channel = request.getHeader("channel");
        response.setContentType("application/json");

        int counter = 0;
        String messageList;
        while ((messageList = messageRepository.getMessageListForReceiverToJSON(receiver, channel)).equals("") && counter < Constants.WAIT_COUNTER) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }
        response.getOutputStream().write(messageList.getBytes(StandardCharsets.UTF_8));
        messageRepository.clearMessagesDeliveredToReceiver(receiver);
    }
}