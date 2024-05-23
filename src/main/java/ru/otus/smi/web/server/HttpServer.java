package ru.otus.smi.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.JDBC.DBClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger log = LogManager.getLogger(HttpServer.class.getName());
    private int port;
    private Dispatcher dispatcher;
    public HttpServer(int port) {
        this.port = port;
    }
    private JDBCService jdbcService;
    public JDBCService getJDBCService() {
        return jdbcService;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Server running on port: " + port);
            this.dispatcher = new Dispatcher();
            log.info("Dispatcher initialized");
            this.jdbcService = new DBClient(statements);
            DBClient.connectJDBC();
            log.info("Database connected " + jdbcService.getClass().getSimpleName());
            while (true) {
                Socket socket = serverSocket.accept();
                ExecutorService executor = Executors.newCachedThreadPool();
                executor.submit(() -> {
                    log.debug("New client connected");
                    try {
                        byte[] buffer = new byte[8192];
                        int n = socket.getInputStream().read(buffer);
                        if (n > 0) {
                            String rawRequest = new String(buffer, 0, n);
                            HttpRequest request = new HttpRequest(rawRequest);
                            request.info(true);
                            dispatcher.execute(request, socket.getOutputStream(), this.jdbcService);
                        }
                    } catch (IOException e) {
                        log.error("Error when working with connected client", e);
                    } finally {
                        if (socket != null) {
                            try {
                                socket.close();
                                log.debug("Close client socket");
                            } catch (IOException e) {
                                log.error("Error closing socket", e);
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {
            log.fatal("Error create socket");
        } catch (SQLException e) {
            log.fatal("Error start " + jdbcService.getClass().getSimpleName());
        }
    }
}