package ru.otus.smi.web.server;

import ru.otus.smi.web.server.JDBC.JDBCService;
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
        this.router.put("GET /calc", new CalculatorRequestProcessor());
        this.router.put("GET /hello", new HelloWorldRequestProcessor());
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("GET /item", new GetProductProcessor());
        this.router.put("POST /item", new CreateNewProductProcessor());
        this.router.put("PUT /item", new EditProductProcessor());
        this.router.put("DELETE /item", new DelProductProcessor());
        this.router.put("GET /file", new ReturnFileProcessor());
        this.unknownOperationRequestProcessor = new UnknownOperationRequestProcessor();
        this.optionsRequestProcessor = new OptionsRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream, JDBCService jdbcService) throws IOException {
        if (httpRequest.getMethod() == HttpMethod.OPTIONS) {
            optionsRequestProcessor.execute(httpRequest, outputStream, jdbcService);
            return;
        }

        if (!router.containsKey(httpRequest.getRouteKey())) {
            unknownOperationRequestProcessor.execute(httpRequest, outputStream, jdbcService);
            return;
        }
        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream, jdbcService);
    }
}
