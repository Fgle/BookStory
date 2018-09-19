package web.client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.impl.BusinessService;
import service.impl.BusinessServiceImpl;
import domain.Cart;

import java.io.IOException;

public class ListCartServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
