package ru.otus.smi.web.server.JDBC;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.application.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JDBC implements JDBCService {
    private static final Logger log = LogManager.getLogger(JDBCService.class.getName());
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String ITEMS_QUERY = "SELECT * FROM product_data";
    private static final String GET_ITEM_QUERY = "SELECT title, price FROM product_data WHERE id = ?";
    private static final String ADD_ITEM_QUERY = "INSERT INTO product_data (title, price) VALUES (?, ?)";
    private static final String EDIT_ITEM_QUERY = "UPDATE product_data SET title = ?, price = ? WHERE id::uuid = ?";
    private static final String DEL_ITEM_QUERY = "DELETE FROM product_data WHERE id = ?";
    private static Connection connection;

    public static void connectJDBC() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL, "postgres", "123");
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet usersResultSet = statement.executeQuery(ITEMS_QUERY)) {
                while (usersResultSet.next()) {
                    UUID id = usersResultSet.getObject(1, UUID.class);
                    String title = usersResultSet.getString(2);
                    int price = usersResultSet.getInt(3);
                    Item item = new Item(id, title, price);
                    items.add(item);
                }
                return items;
            }
        } catch (SQLException e) {
            log.fatal("Error get item from base");
        }
        log.error("No items found");
        return null;
    }

    @Override
    public Item getItemById(UUID searchID) {
        try (PreparedStatement ps = connection.prepareStatement(GET_ITEM_QUERY)) {
            ps.setObject(1, searchID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UUID id = rs.getObject(1, UUID.class);
                    String title = rs.getString(2);
                    int price = rs.getInt(3);
                    return new Item(id, title, price);
                }
            }
        } catch (SQLException e) {
            log.fatal("Error get item from base");
        }
        log.error("Item with such ID wasn't found");
        return null;
    }

    @Override
    public void addItems(Item item) {
        try (PreparedStatement ps = connection.prepareStatement(ADD_ITEM_QUERY)) {
            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.fatal("Error add item in base");
        }
    }

    @Override
    public void editItem(Item newItem) {
        try (PreparedStatement ps = connection.prepareStatement(EDIT_ITEM_QUERY)) {
            ps.setString(1, newItem.getTitle());
            ps.setInt(2, newItem.getPrice());
            ps.setObject(3, newItem.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.fatal("Error edit item in base");
        }
    }

    @Override
    public void delInit(UUID id) {
        try (PreparedStatement ps = connection.prepareStatement(DEL_ITEM_QUERY)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.fatal("Error delete item from base");
        }
    }
}