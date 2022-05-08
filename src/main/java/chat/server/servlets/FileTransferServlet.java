package chat.server.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "FileTransfer", urlPatterns = "/file")
public class FileTransferServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sender = request.getHeader("sender");
        String fileName = "[" + sender + "]" + request.getHeader("fileName");

        File saveFile = new File(fileName);

        InputStream inputStream = request.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(saveFile);

        byte[] buffer = new byte[1024];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();

        String newFilePath = saveFile.getAbsolutePath();
        response.getWriter().print(newFilePath);
        response.setHeader("filePath", newFilePath);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientName = request.getHeader("client");
        String filePath = request.getHeader("filePath");
        String fileName = "[" + clientName + "]" + request.getHeader("fileName");

        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        byte[] data = new byte[1024];
        int count;

        while ((count = bufferedInputStream.read(data, 0, 1024)) != -1) {
            fileOutputStream.write(data, 0, count);
        }

        fileInputStream.close();
        bufferedInputStream.close();
        fileOutputStream.close();
    }
}