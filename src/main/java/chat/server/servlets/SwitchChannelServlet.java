package chat.server.servlets;

import chat.server.entities.User;
import chat.server.repositories.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/switch-channel

HEADER: username
VALUE: {username}

HEADER: channel
VALUE: {channelName}
*/

@WebServlet(name = "SwitchChannel", urlPatterns = "/switch-channel")
public class SwitchChannelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = UserRepository.getInstance().getAllUsers();
        String userName = request.getHeader("username");
        String channel = request.getHeader("channel");

        for (User user : users) {
            if (user.getName().equals(userName))
                user.setCurrentChannel(channel);

            response.setHeader("channel", "switched");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
