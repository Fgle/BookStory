package web.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
import utils.WebUtils;
import domain.User;

public class RegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String cellphone = request.getParameter("cellphone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");

            User user = new User();
            user.setAddress(address);
            user.setCellphone(cellphone);
            user.setEmail(email);
            user.setId(WebUtils.makeID());
            user.setPassword(password);
            user.setPhone(phone);
            user.setUsername(username);

            BusinessServiceImpl service = new BusinessServiceImpl();
            User usersession = (User) request.getSession().getAttribute("user");
            User isExistedUser = service.findUserByName(username);

            if(isExistedUser != null) {
                request.setAttribute("message", "用户以存在！");
                request.setAttribute("path", "/client/register.jsp");
                request.getRequestDispatcher("/message").forward(request, response);
                return;
            }
            if(usersession == null) {
                request.getSession().setAttribute("user",user);
            }
            service.registerUser(user);
            request.getRequestDispatcher("/client/head.jsp").forward(request, response);

        }catch(Exception e){
            e.printStackTrace();
            request.setAttribute("message", "注册失败");
            request.setAttribute("path","/client/head.jsp");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
