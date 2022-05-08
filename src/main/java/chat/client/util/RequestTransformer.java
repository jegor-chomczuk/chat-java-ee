package chat.client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RequestTransformer {

    public static byte[] inputStreamToArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        do {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) byteArrayOutputStream.write(buffer, 0, bytesRead);
        } while (bytesRead != -1);

        return byteArrayOutputStream.toByteArray();
    }
}
