package web.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.impl.BusinessServiceImpl;
import domain.Order;

public class OrderServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        if("list".equalsIgnoreCase(method)) {
            list(request, response);
        } else if("detail".equalsIgnoreCase(method)) {
            detail(request, response);
        } else if("del".equalsIgnoreCase(method)) {
            delete(request, response);
        }

    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userid = request.getParameter("userID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        List<Order> orders = service.clientListOrder(userid);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/client/clientlistorder.jsp").forward(request, response);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderid = request.getParameter("orderID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Order order = service.findOrder(orderid);
        request.setAttribute("order", order);
        request.getRequestDispatcher("/client/clientorderdetail.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order_id = request.getParameter("orderID");
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
