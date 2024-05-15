package ru.otus.smi.web.server.application.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;
import ru.otus.smi.web.server.application.Storage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class GetProductProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(GetProductProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        UUID id = UUID.fromString(httpRequest.getParameter("id"));
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nAccess-Control-Allow-Origin: *\r\n\r\n"
                        + gson.toJson(Storage.getItemById(id));
        output.write(result.getBytes(StandardCharsets.UTF_8));
        log.debug("Item returned");
    }
}
