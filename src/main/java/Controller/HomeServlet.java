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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", "/"})
public class HomeServlet extends HttpServlet {

    private final NewsDAO newsDAO = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> listCate = categoryDAO.getAll();
            List<News> listHot = newsDAO.getTop5Hot();
            List<News> listNewest = newsDAO.getTop5Newest();
            List<News> listHome = newsDAO.getHomeNews();

            request.setAttribute("listCate", listCate);
            request.setAttribute("listHot", listHot);
            request.setAttribute("listNewest", listNewest);
            request.setAttribute("listHome", listHome);

            request.setAttribute("contentPage", "/view/guest/home.jsp");
            request.getRequestDispatcher("/layout/main.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống, vui lòng thử lại sau!");
        }
    }
}