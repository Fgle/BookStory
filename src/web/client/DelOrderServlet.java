package web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Order;
import service.impl.BusinessServiceImpl;
import domain.Cart;
import domain.User;

public class DelOrderServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order_id = request.getParameter("order_id");
        User user = (User) request.getSession().getAttribute("user");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Order order = service.findOrder(order_id);
        service.delOrder(order);
        List<Order> orders = service.clientListOrder(user.getId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/client/clientlistorder.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
