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

import java.util.ArrayList;
import java.util.List;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/users
*/

@WebServlet(name = "GetAllUsers", urlPatterns = "/users")
public class GetAllUsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<User> users = UserRepository.getInstance().getAllUsers();

        List<String> usersInfo = new ArrayList<>();
        for (User user : users) {
            usersInfo.add(user.toString());
        }

        OutputStream output = response.getOutputStream();

        for (String info : usersInfo) {
            output.write(info.getBytes(StandardCharsets.UTF_8));
            output.write("\r\n".getBytes());
        }
    }
}