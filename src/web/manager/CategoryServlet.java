package web.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.impl.BusinessServiceImpl;
import utils.WebUtils;
import domain.Category;

//处理分类的CRUD请求
public class CategoryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        if ("add".equalsIgnoreCase(method)) {
            add(request, response);
        } else if ("delete".equalsIgnoreCase(method)) {
            delete(request, response);
        } else if ("update".equalsIgnoreCase(method)) {
            revise(request, response);
        } else if ("find".equalsIgnoreCase(method)) {
            find(request, response);
        } else if ("listAll".equalsIgnoreCase(method)) {
            listAll(request, response);
        } else if ("reviseUI".equalsIgnoreCase(method)) {
            reviseUI(request, response);
        } else {
            request.setAttribute("message", "不支持此类操作");
            request.getRequestDispatcher("/message.jsp").forward(request,
                    response);
        }
    }

    private void listAll(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        BusinessServiceImpl service = new BusinessServiceImpl();
        List<Category> CategoryList = service.getAllCategory();
        request.setAttribute("categories", CategoryList);
        request.getRequestDispatcher("/manage/listcategory.jsp").forward(request, response);
    }

    private void find(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    private void revise(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        String categoryID = request.getParameter("categoryID");
        BusinessServiceImpl service = new BusinessServiceImpl();


    }

    private void reviseUI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String categoryID = request.getParameter("categoryID");
        BusinessServiceImpl service = new BusinessServiceImpl();
        Category category = service.findCategory(categoryID);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/manage/categoryInfo.jsp").forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub

    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            category.setId(WebUtils.makeID());

            BusinessServiceImpl service = new BusinessServiceImpl();
            service.addCategory(category);
            request.setAttribute("path","/manage/CategoryServlet?method=listAll");
            request.setAttribute("message", "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("path","/manage/categoryInfo.jsp");
            request.setAttribute("message", "添加失败");
        }
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
