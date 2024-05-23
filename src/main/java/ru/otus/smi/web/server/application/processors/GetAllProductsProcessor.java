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

public class GetAllProductsProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(GetAllProductsProcessor.class.getName());
    private final DBClient dbClient;
    public GetAllProductsProcessor() {
        this.dbClient = new DBClient();
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        List<Item> items = dbClient.getItems();
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + gson.toJson(items);
        output.write(result.getBytes(StandardCharsets.UTF_8));
        log.debug("Generated list of items and sent it");
    }
}
