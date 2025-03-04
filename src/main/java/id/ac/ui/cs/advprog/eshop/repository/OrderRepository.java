package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import java.util.List;

public interface OrderRepository {
    public Order save(Order order);
    public Order findById(String id);
    public List<Order> findAllByAuthor(String author);
}

