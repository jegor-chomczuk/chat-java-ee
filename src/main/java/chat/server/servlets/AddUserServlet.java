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

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/add-user

HEADER: username
VALUE: {username}
*/

@WebServlet(name = "AddUser", urlPatterns = "/add-user")
public class AddUserServlet extends HttpServlet {
    private UserRepository userRepository = UserRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userName = request.getHeader("username");
        OutputStream output = response.getOutputStream();

        if (!userName.isEmpty()) {
            if (UserRepository.isUsernameTaken(userName)) {
                response.setStatus(400);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
                userRepository.add(new User(userName));
                response.setStatus(200);
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            String errorMessage = "Username already taken. Try again with another username.";
            output.write(errorMessage.getBytes(StandardCharsets.UTF_8));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}