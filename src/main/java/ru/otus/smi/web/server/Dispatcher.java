package ru.otus.smi.web.server;

import ru.otus.smi.web.server.application.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownOperationRequestProcessor;
    private RequestProcessor optionsRequestProcessor;


    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("GET /item", new GetProductProcessor());
        this.router.put("POST /items", new CreateNewProductProcessor());
        this.router.put("PUT /items", new EditProductProcessor());
        this.router.put("DELETE /items", new DelProductProcessor());
        this.router.put("GET /file", new ReturnFileProcessor());
        this.unknownOperationRequestProcessor = new UnknownOperationRequestProcessor();
        this.optionsRequestProcessor = new OptionsRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        if (httpRequest.getMethod() == HttpMethod.OPTIONS) {
            optionsRequestProcessor.execute(httpRequest, outputStream);
            return;
        }

        if (!router.containsKey(httpRequest.getRouteKey())) {
            unknownOperationRequestProcessor.execute(httpRequest, outputStream);
            return;
        }
        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream);
    }
}
