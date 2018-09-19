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

public class BuyServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String bookid = request.getParameter("bookid");
            BusinessServiceImpl service = new BusinessServiceImpl();
            Book book = service.findBook(bookid);
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            User user = (User) request.getSession().getAttribute("user");
            if(user == null){
                request.setAttribute("message","请登录！");
                request.setAttribute("path","/client/IndexServlet");
                request.getRequestDispatcher("/message.jsp").forward(request, response);
                return;
            }
            if(cart == null){
                cart = new Cart();
                request.getSession().setAttribute("cart", cart);
            }
            service.buyBook(cart, book, user);
            response.sendRedirect("/client/IndexServlet?method=getAll");
            return;
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("message", "购买失败");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
