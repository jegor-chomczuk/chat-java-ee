package chat.server.servlets;

import chat.server.entities.Message;
import chat.server.repositories.MessageRepository;
import chat.server.util.History;
import chat.server.util.RequestTransformer;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.nio.charset.StandardCharsets;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/send-message

HEADER: channel
VALUE: {currentChannel}

HEADER: sender
VALUE: {userName}

BODY:
{
    "color" : "[40m"
    "channel" : "{channelName}",
    "sender" : "{userName}",
    "message" : "{message}"
}
*/

@WebServlet(name = "SendMessage", urlPatterns = "/send-message")
public class SendMessageServlet extends HttpServlet {
    @EJB
    private History history;
    private MessageRepository messageRepository = MessageRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        byte[] buffer = RequestTransformer.requestBodyToArray(request);
        String bufferString = new String(buffer, StandardCharsets.UTF_8);

        Message message = Message.fromJSON(bufferString);
        String channel = request.getHeader("channel");
        String sender = request.getHeader("sender");

        if (message != null) {
            messageRepository.addReceiver(channel);
            messageRepository.addMessage(sender, channel, message);
            response.setStatus(200);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setStatus(400);
        }
        history.writeToChatHistory(message + "\n");
    }
}