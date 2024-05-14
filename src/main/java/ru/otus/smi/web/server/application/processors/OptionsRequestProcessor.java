package ru.otus.smi.web.server.application.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class OptionsRequestProcessor implements RequestProcessor{
    private static final Logger log = LogManager.getLogger(OptionsRequestProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response = "HTTP/1.1 204 OK\r\n" +
                          "Access-Control-Request-Method: POST, DEL\r\n" +
                          "Access-Control-Allow-Headers: X-Requested-With\r\n" +
                          "Access-Control-Allow-Origin: *\r\n\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
        log.debug("Answered OPTIONS");
    }
}
