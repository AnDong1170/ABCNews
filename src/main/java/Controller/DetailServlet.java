package Controller;

import Dao.NewsDAO;
import Model.News;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail")
public class DetailServlet extends HttpServlet {

    private final NewsDAO newsDAO = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        try {
            News news = newsDAO.findById(id);
            if (news == null) {
                response.sendRedirect("home");
                return;
            }

            // Tăng lượt xem
            newsDAO.increaseView(id);

            request.setAttribute("news", news);
            request.setAttribute("listSameCate", newsDAO.getByCategory(news.getCategoryId()));

            request.setAttribute("contentPage", "/view/guest/detail.jsp");
            request.getRequestDispatcher("/layout/main.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home");
        }
    }
}