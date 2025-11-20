package Controller;

import Dao.CategoryDAO;
import Model.Category;
import Model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/category")
public class AdminCategoryServlet extends HttpServlet {
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkAdmin(request, response);

        try {
            List<Category> list = categoryDAO.getAll();
            request.setAttribute("listCate", list);
            request.setAttribute("contentPage", "/view/admin/category-manager.jsp");
            request.getRequestDispatcher("/layout/admin-main.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkAdmin(request, response);

        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String name = request.getParameter("name");

        Category c = new Category();
        c.setId(id);
        c.setName(name);

        try {
            if ("delete".equals(action)) {
                categoryDAO.delete(id);
            } else if ("update".equals(action)) {
                categoryDAO.update(c);
            } else {
                categoryDAO.insert(c);
            }
            response.sendRedirect("category");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isRole()) {
            response.sendRedirect("../login");
        }
    }
}