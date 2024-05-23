package ru.otus.smi.web.server.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DBConnect {
    private static final Logger log = LogManager.getLogger(DBConnect.class.getName());

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";

    public final Connection connection;

    public DBConnect() {
        try {
            this.connection = getConnection(DATABASE_URL, "postgres", "123");
        } catch (SQLException e) {
            log.error("Error connect to base. " + e.getMessage());
            throw new RuntimeException();
        }
    }
}
