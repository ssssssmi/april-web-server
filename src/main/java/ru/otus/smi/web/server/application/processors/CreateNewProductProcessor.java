package ru.otus.smi.web.server.application.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;
import ru.otus.smi.web.server.JDBC.JDBCService;
import ru.otus.smi.web.server.application.Item;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CreateNewProductProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(CreateNewProductProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, JDBCService jdbcService) throws IOException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        jdbcService.addItems(item);
        List<Item> items = jdbcService.getItems();
        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\n" +
                "Access-Control-Allow-Origin: *\r\n\r\n" + gson.toJson(items);
        output.write(response.getBytes(StandardCharsets.UTF_8));
        log.debug("Add item and send it with id");
    }
}
