package ru.otus.smi.web.server.application.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ReturnFileProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(ReturnFileProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String text = "";
        try (FileInputStream fileInputStream = new FileInputStream("static/" + httpRequest.getParameter("name"))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                text = new String(buffer, 0, len, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: multipart/form-data\r\nAccess-Control-Allow-Origin: *\r\n\r\n" + gson.toJson(text);
        output.write(result.getBytes(StandardCharsets.UTF_8));
        log.debug("Document sent");
    }
}

