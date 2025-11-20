package Controller;

import Dao.CategoryDAO;
import Dao.NewsDAO;
import Model.Category;
import Model.News;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    private final NewsDAO newsDAO = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        try {
            List<Category> listCate = categoryDAO.getAll();
            List<News> listNews = newsDAO.getByCategory(id);

            request.setAttribute("listCate", listCate);
            request.setAttribute("listNews", listNews);
            request.setAttribute("currentCateName", categoryDAO.findById(id).getName());

            request.setAttribute("contentPage", "/view/guest/category.jsp");
            request.getRequestDispatcher("/layout/main.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }
}