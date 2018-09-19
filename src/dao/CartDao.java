package dao;

import java.util.List;
import domain.Cart;
import domain.Book;
import domain.User;

public interface CartDao {
    void add(Cart cart);

    Cart find(String id);

}
