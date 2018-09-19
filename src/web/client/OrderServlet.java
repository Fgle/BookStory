package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
import domain.Cart;
import domain.User;

public class OrderServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            User user = (User) request.getSession().getAttribute("user");
            if(user == null){
                request.setAttribute("message", "对不起，请先登录");
                request.getRequestDispatcher("/message.jsp").forward(request, response);
                return;
            }

            Cart cart = (Cart) request.getSession().getAttribute("cart");
            BusinessServiceImpl service = new BusinessServiceImpl();
            service.createOrder(cart, user);
            service.clearCart(cart);//清空购物车

            request.setAttribute("message", "订单已生成");
            request.setAttribute("path","/client/ClientListOrderServlet?userid=" + user.getId());
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("message", "订单生成失败");
            request.setAttribute("path","/client/ListCartServlet");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
