package ru.otus.smi.web.server.application.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReturnFileProcessor implements RequestProcessor {
    private static final Logger log = LogManager.getLogger(ReturnFileProcessor.class.getName());

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String filename = httpRequest.getParameter("name").substring(1);
        Path filePath = Paths.get("static/", filename);
        String fileType = filename.substring(filename.lastIndexOf(".") + 1);
        byte[] fileData = Files.readAllBytes(filePath);
        String contentDisposition = "";
        if (fileType.equals("pdf")) {
            contentDisposition = "Contetn-Disposition: attachment;filename=" + filename + "\r\n";
        }
        String result = "HTTP/1.1 200 OK\r\nContent-Lenght: " + fileData.length + "\r\n" + contentDisposition + "\r\n";
        output.write(result.getBytes());
        output.write(fileData);
        log.debug("Document sent");
    }
}

