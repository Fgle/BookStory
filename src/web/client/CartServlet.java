package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import domain.Book;
import domain.Cart;
import domain.User;
import service.impl.BusinessServiceImpl;

public class CartServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");

        if("add".equalsIgnoreCase(method)) {
            add(request, response);
        }else if("rmOneBook".equalsIgnoreCase(method)) {
            removeOneBook(request, response);
        }else if("delBooks".equalsIgnoreCase(method)) {
            deleteBooks(request, response);
        } else if("list".equalsIgnoreCase(method)) {
            list(request, response);
        } else if("order".equalsIgnoreCase(method)) {
            order(request, response);
        }
    }
    /***列出购物车信息***/
    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BusinessServiceImpl service = new BusinessServiceImpl();
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            request.setAttribute("message","请登录！！！");
            request.setAttribute("path","/client/IndexServlet?method=getAll");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        }
        Cart cart = service.findCart(user.getId());
        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request,response);
    }
    /***删除购物车中一本图书***/
    private void removeOneBook(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        String bookID = request.getParameter("bookID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        Book book = service.findBook(bookID);
        service.removeOneBookInCart(cart, book);

        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);

    }
    /***删除购物车中某图书***/
    private void deleteBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookID = request.getParameter("bookID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        Book book = service.findBook(bookID);
        service.deleteBooksInCart(cart, book);

        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);

    }
    /***向购物车中添加一本已有图书***/
    private void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookID= request.getParameter("bookID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("user");

        Book book = service.findBook(bookID);
        service.buyBook(cart, book, user);

        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);

    }

    private void order(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            User user = (User) request.getSession().getAttribute("user");
            if(user == null){
                request.setAttribute("message", "对不起，请先登录");
                request.setAttribute("message", "/client/body.jsp");
                request.getRequestDispatcher("/message.jsp").forward(request, response);
                return;
            }

            Cart cart = (Cart) request.getSession().getAttribute("cart");
            BusinessServiceImpl service = new BusinessServiceImpl();
            service.createOrder(cart, user);
            service.clearCart(cart);//清空购物车

            request.setAttribute("message", "订单已生成");
            request.setAttribute("path","/client/OrderServlet?method=list&userID=" + user.getId());
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("message", "订单生成失败");
            request.setAttribute("path","/client/CartServlet?method=list");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
