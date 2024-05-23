package ru.otus.smi.web.server.JDBC;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.smi.web.server.application.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DBClient {
    private static final Logger log = LogManager.getLogger(DBClient.class.getName());

    private final Statements statements;

    public DBClient() {
        statements = new Statements();
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        try (ResultSet usersResultSet = statements.getItems()) {
            while (usersResultSet.next()) {
                UUID id = usersResultSet.getObject(1, UUID.class);
                String title = usersResultSet.getString(2);
                int price = usersResultSet.getInt(3);
                Item item = new Item(id, title, price);
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            log.fatal("Error get item from base");
        }
        log.error("No items found");
        return null;
    }

    public Item getItemById(UUID searchID) {
        try (PreparedStatement ps = statements.getItemsByIDQuery()) {
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

    public void addItems(Item item) {
        try (PreparedStatement ps = statements.addItemsQuery()) {
            ps.setString(1, item.getTitle());
            ps.setInt(2, item.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.fatal("Error add item in base");
        }
    }

    public void editItems(Item newItem) {
        try (PreparedStatement ps = statements.editItemsByIDQuery()) {
            ps.setString(1, newItem.getTitle());
            ps.setInt(2, newItem.getPrice());
            ps.setObject(3, newItem.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.fatal("Error edit item in base");
        }
    }

    public void deleteItems(UUID id) {
        try (PreparedStatement ps = statements.deleteItemsByIDQuery()) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.fatal("Error delete item from base");
        }
    }
}