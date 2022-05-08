package chat.server.servlets;

import chat.server.repositories.UserRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
http://localhost:8080/java-ee-chat-1.0-SNAPSHOT/remove-user

HEADER: username
VALUE: {username}
*/

@WebServlet(name = "RemoveUser", urlPatterns = "/remove-user")
public class RemoveUserServlet extends HttpServlet {
    private UserRepository userRepository = UserRepository.getInstance();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getHeader("username");
        if (!userName.isEmpty()) {
            userRepository.removeUser(userName);
            response.setHeader("user", "removed");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}