package web.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Order;
import service.impl.BusinessServiceImpl;

public class ManagerOrderServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String method = request.getParameter("method");
        if("list".equalsIgnoreCase(method))
            list(request, response);
        if("del".equalsIgnoreCase(method))
            delete(request, response);

    }

    public void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String state = request.getParameter("state");
        BusinessServiceImpl service = new BusinessServiceImpl();
        List<Order> orders = service.listOrder(state);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/manage/listorder.jsp").forward(request, response);
        return;
    }

    public void delete(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        String orderID = request.getParameter("orderID");
        String state = request.getParameter("state");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Order order = service.findOrder(orderID);
        service.delOrder(order);
        List<Order> orders = service.listOrder(state);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/manage/listorder.jsp").forward(request, response);
        return;
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
