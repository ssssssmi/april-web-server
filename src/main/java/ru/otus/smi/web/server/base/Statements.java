package ru.otus.smi.web.server.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Statements {
    private final DBConnect dBconnect;

    public Statements() {
        this.dBconnect = new DBConnect();
    }

    public ResultSet getItems() throws SQLException {
        Statement statement = dBconnect.connection.createStatement();
        return statement.executeQuery("SELECT * FROM product_data");
    }

    public PreparedStatement getItemsByIDQuery() throws SQLException {
        return dBconnect.connection.prepareStatement("SELECT title, price FROM product_data WHERE id = ?");
    }

    public PreparedStatement addItemsQuery() throws SQLException {
        return dBconnect.connection.prepareStatement("INSERT INTO product_data (title, price) VALUES (?, ?)");
    }

    public PreparedStatement editItemsByIDQuery() throws SQLException {
        return dBconnect.connection.prepareStatement("UPDATE product_data SET title = ?, price = ? WHERE id::uuid = ?");
    }

    public PreparedStatement deleteItemsByIDQuery() throws SQLException {
        return dBconnect.connection.prepareStatement("DELETE FROM product_data WHERE id = ?");
    }
}
