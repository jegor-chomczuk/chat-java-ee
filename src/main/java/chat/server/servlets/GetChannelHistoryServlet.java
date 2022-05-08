package chat.server.servlets;

import chat.server.entities.User;
import chat.server.repositories.UserRepository;
import chat.server.util.History;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/history

HEADER: channel
VALUE: {channelName}

HEADER: client
VALUE: {clientName}
*/

@WebServlet(name = "GetChannelHistory", urlPatterns = "/history")
public class GetChannelHistoryServlet extends HttpServlet {
    private UserRepository userRepository = UserRepository.getInstance();

    @EJB
    private History history;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String channelName = request.getHeader("channel");
        String clientName = request.getHeader("client");
        int responseStatus = 0;

        for (User user : userRepository.getAllUsers()) {
            if (user.getName().equals(clientName)) {


                for (String channel : user.getChannels()) {
                    if (channel.equals(channelName)) {
                        response.setStatus(200);
                        responseStatus = 200;
                        response.setStatus(HttpServletResponse.SC_OK);
                        OutputStream output = response.getOutputStream();
                        history.readChannelHistory(channelName, output);
                        output.close();
                    }
                }
            }
        }

        if (responseStatus != 200) {
            OutputStream output = response.getOutputStream();
            response.setStatus(400);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            output.close();
        }
    }
}
