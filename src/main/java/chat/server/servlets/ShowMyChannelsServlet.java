package chat.server.servlets;

import chat.server.entities.User;
import chat.server.repositories.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/channels

HEADER: username
VALUE: {username}
*/

@WebServlet(name = "ShowMyChannels", urlPatterns = "/channels")
public class ShowMyChannelsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = UserRepository.getInstance().getAllUsers();
        String userName = request.getHeader("username");
        Set<String> userChannels = new HashSet<>();
        OutputStream output = response.getOutputStream();

        if (!userName.isEmpty()) {
            for (User user : users) {
                if (user.getName().equals(userName))
                    userChannels.addAll(user.getChannels());
            }

            for (String channel : userChannels) {
                output.write(channel.getBytes(StandardCharsets.UTF_8));
                output.write("\r\n".getBytes());
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
