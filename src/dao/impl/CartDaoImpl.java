package dao.impl;

import java.sql.SQLException;
import java.util.*;

import domain.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import utils.JdbcUtils;
import dao.CartDao;
import domain.User;
import domain.Cart;
import domain.CartItem;

public class CartDaoImpl implements CartDao{
    @Override
    public void add(Cart cart) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            //将用户的购物车信息保存到cart表
            String sql = "select * from cart where user_id=?";
            Cart oldcart = (Cart) runner.query(sql, new BeanHandler(Cart.class), cart.getUser().getId());
            Object params[] = null;
            if(oldcart == null) {
                sql = "insert into cart(id,price,user_id) values(?,?,?)";
                params = new Object[] {cart.getId(), cart.getPrice(),cart.getUser().getId() };
            }
            else {
                sql = "update cart set price=? where user_id=?";
                params = new Object[] { cart.getPrice(), cart.getUser().getId() };
            }

            runner.update(sql, params);
            //将cart中的图书项保存到cartitem
            List<CartItem> cartItems = new ArrayList<CartItem>(cart.getMap().values());
            for (CartItem cartItem : cartItems) {
                sql = "select * from cartitem where book_id=?";
                CartItem oldItem = (CartItem) runner.query(sql, new BeanHandler(CartItem.class), cartItem.getBook().getId());
                if (oldItem == null) {
                    sql = "insert into cartitem(id,quantity,price,cart_id,book_id) values(?,?,?,?,?)";
                    params = new Object[] {cartItem.getId(), cartItem.getQuantity(), cartItem.getPrice(), cart.getId(), cartItem.getBook().getId()};
                }
                else {
                    sql = "update cartitem set quantity=?,price=? where book_id=?";
                    params = new Object[] {cartItem.getQuantity(), cartItem.getPrice(), cartItem.getBook().getId() };
                }

                runner.update(sql, params);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Cart find(String id) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            //1.找出购物车的基本信息
            String sql = "select * from cart where user_id=?";
            Cart cart = (Cart) runner.query(sql, new BeanHandler(Cart.class), id);
            if(cart == null)
                return null;
            //2.找出购物车中所有的图书项
            sql = "select * from cartitem where cart_id=?";
            List<CartItem> cartItems = (List<CartItem>) runner.query(sql, new BeanListHandler(CartItem.class), cart.getId());
            for(CartItem item : cartItems){
                sql = "select book.* from cartitem,book where cartitem.id=? and cartitem.book_id=book.id";
                Book book = (Book) runner.query(sql, new BeanHandler(Book.class), item.getId());
                item.setBook(book);
                cart.getMap().put(book.getId(), item);//把找出的图书项放进order
            }
            //把找出的图书项放进cart
            //cart.getCartItems().addAll(cartItems);
            //3.找出购物车属于哪个用户
            sql = "select * from user where id=?";
            User user = (User) runner.query(sql, new BeanHandler(User.class), id);
            cart.setUser(user);
            return cart;
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeOne(Cart cart, Book book) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            String sql = null;
            Object params[] = null;
            String itemID= cart.getMap().get(book.getId()).getId();

            cart.removeOne(book);
            CartItem cartItem = cart.getMap().get(book.getId());

            if(cartItem == null) {
                sql = "delete from cartitem where id=?";
                runner.update(sql, itemID);
            }
            else {
                sql = "update cartitem set quantity=?,price=? where id=?";
                params = new Object[] { cartItem.getQuantity(), cartItem.getPrice(), cartItem.getId()};
                runner.update(sql, params);
            }
            if (cart.getMap().isEmpty()) {
                sql = "delete from cart where id=?";
                runner.update(sql, cart.getId());
            }
            else {
                sql = "update cart set price=? where id=?";
                params = new Object[]{cart.getPrice(), cart.getId()};
                runner.update(sql, params);
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Cart cart, Book book) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
            CartItem cartItem = cart.getMap().get(book.getId());
            cart.delete(book);
            String sql = "delete from cartitem where id=?";
            runner.update(sql, cartItem.getId());
            if (cart.getMap().isEmpty()) {
                sql = "delete from cart where id=?";
                runner.update(sql, cart.getId());
            }
            else {
                sql = "update cart set price=? where id=?";
                Object params[] = {cart.getPrice(), cart.getId()};
                runner.update(sql, params);
            }
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear(Cart cart) {
        try {
          QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
          String sql = "delete from cartitem where cart_id=?";
          runner.update(sql, cart.getId());
          sql = "delete from cart where id = ?";
          runner.update(sql, cart.getId());
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
