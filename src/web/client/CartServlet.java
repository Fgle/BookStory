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

public class CartServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");

        //添加一本图书
        if("add".equalsIgnoreCase(method)) {
            add(request, response);
        }else if("rmOneBook".equalsIgnoreCase(method)) {
            removeOneBook(request, response);
        }else if("delBooks".equalsIgnoreCase(method)) {
            deleteBooks(request, response);
        } else if("list".equalsIgnoreCase(method)) {
            list(request, response);
        }
    }

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

    private void removeOneBook(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        String book_id = request.getParameter("bookID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("user");

        Book book = service.findBook(book_id);
        service.removeOneBookInCart(cart, book);

        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);

    }
    private void deleteBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String book_id = request.getParameter("bookID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("user");

        Book book = service.findBook(book_id);
        service.deleteBooksInCart(cart, book);

        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);

    }

    private void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String book_id = request.getParameter("bookID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("user");

        Book book = service.findBook(book_id);
        service.buyBook(cart, book, user);

        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/client/listcart.jsp").forward(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
