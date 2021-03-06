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
        if("list".equalsIgnoreCase(method)) {
            list(request, response);
        }else if("del".equalsIgnoreCase(method)) {
            delete(request, response);
        }else if("detail".equalsIgnoreCase(method)) {
            detail(request, response);
        }else if("confirm".equalsIgnoreCase(method))
            confirm(request, response);
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String state = request.getParameter("state");
        BusinessServiceImpl service = new BusinessServiceImpl();
        List<Order> orders = service.listOrder(state);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/manage/listorder.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        String orderID = request.getParameter("orderID");
        String state = request.getParameter("state");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Order order = service.findOrder(orderID);
        service.delOrder(order);
        List<Order> orders = service.listOrder(state);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/manage/listorder.jsp").forward(request, response);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        String orderID = request.getParameter("orderID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Order order = service.findOrder(orderID);
        request.setAttribute("order", order);
        request.getRequestDispatcher("/manage/orderdetail.jsp").forward(request, response);
    }

    private void confirm(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        try{
            String orderID = request.getParameter("orderID");
            BusinessServiceImpl service = new BusinessServiceImpl();
            service.confirmOrder(orderID);
            request.setAttribute("message", "订单已置为发货状态，请及时配送");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        } catch(Exception e){
            e.printStackTrace();
            request.setAttribute("message", "确认失败");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
