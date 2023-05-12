package org.kainos.ea.db;

import org.kainos.ea.cli.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public List<Order> getAllOrders() {
        try (Connection c = databaseConnector.getConnection()) {
            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, OrderDate FROM `Order`;");

            List<Order> orderList = new ArrayList<>();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("OrderDate")
                );

                orderList.add(order);
            }

            return orderList;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            return null;
        }
    }
}
