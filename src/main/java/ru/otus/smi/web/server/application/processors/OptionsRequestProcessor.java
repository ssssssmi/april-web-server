package ru.otus.smi.web.server.application.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class OptionsRequestProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(OptionsRequestProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response = "HTTP/1.1 204 No Content\r\n" +
                "Connection: keep-alive\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "Access-Control-Allow-Methods: POST, GET, OPTIONS, DELETE\r\n" +
                "Access-Control-Allow-Headers: *\r\n" +
                "Access-Control-Max-Age: 86400";
        output.write(response.getBytes(StandardCharsets.UTF_8));
        log.debug("Answered OPTIONS");
    }
}
