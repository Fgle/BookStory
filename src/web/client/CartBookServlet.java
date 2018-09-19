package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Book;
import domain.Cart;
import domain.User;
import service.impl.BusinessServiceImpl;

public class CartBookServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        String book_id = request.getParameter("book_id");
        System.out.printf(book_id);
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("user");

        Book book = service.findBook(book_id);
        System.out.printf(book_id);
        //添加一本图书
        if("add".equalsIgnoreCase(method)) {
            service.buyBook(cart, book, user);
            request.getSession().setAttribute("cart",cart);
            request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);
            return;
        }
        //删除一本图书
        if("rm".equalsIgnoreCase(method)) {
            service.removeOneBookInCart(cart, book);
            request.getSession().setAttribute("cart",cart);
            request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);
            return;
        }
        //删除图书
        if("del".equalsIgnoreCase(method)) {
            service.deleteBooksInCart(cart, book);
            request.getSession().setAttribute("cart",cart);
            request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);
            return;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
