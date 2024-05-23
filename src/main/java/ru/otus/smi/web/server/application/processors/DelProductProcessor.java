package ru.otus.smi.web.server.application.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;
import ru.otus.smi.web.server.JDBC.DBClient;
import ru.otus.smi.web.server.application.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class DelProductProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(DelProductProcessor.class.getName());
    private final DBClient dbClient;

    public DelProductProcessor() {
        this.dbClient = new DBClient();
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        UUID id = UUID.fromString(httpRequest.getParameter("id"));
        dbClient.deleteItems(id);
        Gson gson = new Gson();
        List<Item> items = dbClient.getItems();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nAccess-Control-Allow-Origin: *\r\n\r\n" + gson.toJson(items);
        output.write(result.getBytes(StandardCharsets.UTF_8));
        log.debug("Item deleted");
    }
}
