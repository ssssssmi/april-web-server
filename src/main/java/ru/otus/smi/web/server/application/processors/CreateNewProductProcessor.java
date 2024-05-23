package ru.otus.smi.web.server.application.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;
import ru.otus.smi.web.server.base.DBClient;
import ru.otus.smi.web.server.application.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewProductProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(CreateNewProductProcessor.class.getName());
    private final DBClient dbClient;

    public CreateNewProductProcessor() {
        this.dbClient = new DBClient();
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        dbClient.addItems(item);
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nAccess-Control-Allow-Origin: *\r\n\r\n" + gson.toJson(item);
        output.write(response.getBytes(StandardCharsets.UTF_8));
        log.debug("Add item and send it with id");
    }
}