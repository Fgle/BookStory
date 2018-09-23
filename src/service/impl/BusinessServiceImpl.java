package service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import dao.*;
import utils.DaoFactory;
import utils.WebUtils;
import domain.Book;
import domain.Cart;
import domain.CartItem;
import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Page;
import domain.User;

public class BusinessServiceImpl implements BusinessService {

    private CategoryDao categoryDao = DaoFactory.getInstance().createDao("dao.impl.CategoryDaoImpl", CategoryDao.class);
    private BookDao bookDao = DaoFactory.getInstance().createDao("dao.impl.BookDaoImpl", BookDao.class);
    private UserDao userDao = DaoFactory.getInstance().createDao("dao.impl.UserDaoImpl", UserDao.class);
    private OrderDao orderDao = DaoFactory.getInstance().createDao("dao.impl.OrderDaoImpl", OrderDao.class);
    private CartDao cartDao = DaoFactory.getInstance().createDao("dao.impl.CartDaoImpl",CartDao.class);
//category{
    public void addCategory(Category category){
        categoryDao.add(category);
    }

    public Category findCategory(String id){
        return categoryDao.find(id);
    }

    public List<Category> getAllCategory(){
        return categoryDao.getAll();
    }

    public void updateCategory(Category category) { categoryDao.update(category); }

    public void delCategory(String id) {
        categoryDao.delete(id);
    }
//}

//book{
    //添加书
    public Boolean addBook(Book book){
        Book oldBook = bookDao.findByName(book.getName());
        if(oldBook == null) {
            bookDao.add(book);
            return true;
        }
        else
            return false;

    }
    //更新图书信息
    public void updateBookInfo(Book book){
        bookDao.update(book);
    }
    //删除书
    public void delBook(Book book) { bookDao.delete(book); }
    public void delBookByID(String id) { bookDao.deleteById(id); }
    //查找图书
    public Book findBook(String id){
        return bookDao.findByID(id);
    }
//}

//page{
    //获得所有图书分页数据
    public Page getBookPageData(String pagenum){
        int totalrecord = bookDao.getTotalRecord();
        Page page = null;
        if(pagenum == null){
            page = new Page(1,totalrecord);
        }else{
            page = new Page(Integer.parseInt(pagenum), totalrecord);
        }
        List<Book> list = bookDao.getPageData(page.getStartindex(), page.getPagesize());
        page.setList(list);
        return page;
    }
    //获得某分类图书的分页数据
    public Page getBookPageData(String pagenum, String category_id){
        int totalrecord = bookDao.getTotalRecord(category_id);
        Page page = null;
        if(pagenum == null){
            page = new Page(1,totalrecord);
        }else{
            page = new Page(Integer.parseInt(pagenum), totalrecord);
        }
        List<Book> list = bookDao.getPageData(page.getStartindex(), page.getPagesize(), category_id);
        page.setList(list);
        return page;
    }
//}

//cart{
     //向购物车中添加一本图书
    public void buyBook(Cart cart, Book book, User user) {
        if(cart.getId() == null) {
            cart.setId(WebUtils.makeID());
            cart.setUser(user);
        }
        if(cart.getUser() == null) {
            cart.setUser(user);
        }
        cart.add(book);

        cartDao.add(cart);
    }
    //查找某用户的购物车
    public Cart findCart(String user_id) {
        return cartDao.find(user_id);
    }
    //清除购物车信息
    public void clearCart(Cart cart) {
        cartDao.clear(cart);
    }
    //删除购物车中的一本书
    public void removeOneBookInCart(Cart cart, Book book) {
        cartDao.removeOne(cart, book);
    }
    //删除购物车中的某本书
    public void deleteBooksInCart(Cart cart, Book book) {
        cartDao.delete(cart, book);
    }
//}

//user{
    //注册用户
    public void registerUser(User user) {
        userDao.add(user);
    }
    //查找用户
    public User findUser(String id){ return userDao.find(id); }
    public User findUserByName(String username) { return userDao.find(username); }
    //用户登录
    public User userLogin(String username, String password){
        return userDao.find(username, password);
    }
//}

//order{
    //生成订单
    public void createOrder(Cart cart, User user){
        if(cart == null){
            throw new RuntimeException("对不起，您还没有购买任何商品");
        }
        Order order = new Order();
        order.setId(WebUtils.makeID());
        order.setOrdertime(new Date());
        order.setPrice(cart.getPrice());
        order.setState(false);
        order.setUser(user);
        for(Map.Entry<String, CartItem> me : cart.getMap().entrySet()){
            //得到一个购物项就生成一个订单项
            CartItem citem = me.getValue();
            OrderItem oitem = new OrderItem();
            oitem.setBook(citem.getBook());
            oitem.setPrice(citem.getPrice());
            oitem.setId(WebUtils.makeID());
            oitem.setQuantity(citem.getQuantity());
            order.getOrderitems().add(oitem);
        }
        orderDao.add(order);
    }

    //后台获取所有订单信息
    public List<Order> listOrder(String state) {
        return orderDao.getAll(Boolean.parseBoolean(state));
    }

    //列出订单明细
    public Order findOrder(String orderid) {
        return orderDao.find(orderid);
    }

    //把订单置为发货状态
    public void confirmOrder(String orderid) {
        Order order = orderDao.find(orderid);
        order.setState(true);
        orderDao.update(order);
    }

    //获得某个用户的订单信息
    public List<Order> listOrder(String state, String userid) {
        return orderDao.getAll(Boolean.parseBoolean(state), userid);
    }

    //获取某个用户的订单信息
    public List<Order> clientListOrder(String userid) {
        return orderDao.getAllOrder(userid);
    }
    //删除一个订单
    public void delOrder(Order order) {
        orderDao.delete(order);
    }
//}
}
