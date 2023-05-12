package org.kainos.ea.api;

import org.kainos.ea.cli.Order;
import org.kainos.ea.db.OrderDao;

import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public List<Order> getAllOrders() {
        List<Order> orderList = orderDao.getAllOrders();

        return orderList;
    }
}
