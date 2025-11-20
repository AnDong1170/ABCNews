package Controller;

import Dao.NewsDAO;
import Model.News;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/news")
public class AdminNewsServlet extends HttpServlet {
    private final NewsDAO newsDAO = new NewsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isRole()) { // không phải admin
            response.sendRedirect("../login");
            return;
        }

        try {
            List<News> listNews = newsDAO.getAll(); // anh sẽ gửi method getAll() trong NewsDAO ở cuối tin nhắn này
            request.setAttribute("listNews", listNews);
            request.setAttribute("contentPage", "/view/admin/news-manager.jsp");
            request.getRequestDispatcher("/layout/admin-main.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}