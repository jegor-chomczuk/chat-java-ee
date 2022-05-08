package chat.server.util;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Startup
@Singleton
public class History {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public History() {
        File historyFile = new File("history.txt");
    }

    public void writeToChatHistory(String historyRecord) throws IOException {
        lock.writeLock().lock();
        try {
            try (FileWriter fileWriter = new FileWriter("history.txt", true)) {
                fileWriter.write(historyRecord);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void readChannelHistory(String channel, OutputStream output) throws IOException {
        lock.readLock().lock();

        InputStream inputStream = new FileInputStream("history.txt");
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        try {
            while (line != null) {
                if (line.startsWith("[" + channel + "]")) {
                    output.write(line.getBytes(StandardCharsets.UTF_8));
                    output.write("\r\n".getBytes());
                }
                line = reader.readLine();
            }
        } finally {
            inputStream.close();
            inputStreamReader.close();
            reader.close();
            lock.readLock().unlock();
        }
    }
}