package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import domain.*;
import utils.WebUtils;

public interface BusinessService {

//category{
    void addCategory(Category category);

    Category findCategory(String id);

    List<Category> getAllCategory();

    void updateCategory(Category category);

    void delCategory(String id);
//}

//book{
    //添加书
    Boolean addBook(Book book);
    //更新图书信息
    void updateBookInfo(Book book);
    //删除书
    void delBook(Book book);
    void delBookByID(String id);
    //查找图书
    Book findBook(String id);
//}

//page{
    //获得所有图书分页数据
    Page getBookPageData(String pagenum);
    //获得某分类图书的分页数据
    Page getBookPageData(String pagenum, String category_id);
//}

    //cart{
    //向购物车中添加一本图书
    void buyBook(Cart cart, Book book, User user) ;
    //查找某用户的购物车
    Cart findCart(String user_id);
    //清除购物车信息
    void clearCart(Cart cart);
    //删除购物车中的一本书
    void removeOneBookInCart(Cart cart, Book book);
    //删除购物车中的某本书
    void deleteBooksInCart(Cart cart, Book book);
//}

//user{
    //注册用户
    void registerUser(User user);
    //查找用户
    User findUser(String id);
    User findUserByName(String username);
    //用户登录
    User userLogin(String username, String password);
//}

//order{
    //生成订单
    void createOrder(Cart cart, User user);
    //后台获取所有订单信息
    List<Order> listOrder(String state);

    //列出订单明细
    Order findOrder(String orderid);

    //把订单置为发货状态
    void confirmOrder(String orderid);

    //获得某个用户的订单信息
    List<Order> listOrder(String state, String userid);

    //获取某个用户的订单信息
    List<Order> clientListOrder(String userid);

    //删除一个订单
    void delOrder(Order order);
//}
}