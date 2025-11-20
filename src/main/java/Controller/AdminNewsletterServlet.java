package Controller;

import Dao.NewsletterDAO;
import Model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/newsletter")
public class AdminNewsletterServlet extends HttpServlet {
    private final NewsletterDAO newsletterDAO = new NewsletterDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkAdmin(request, response);

        try {
            List<String> listEmail = newsletterDAO.getAllEnabledEmails();
            request.setAttribute("listEmail", listEmail);
            request.setAttribute("contentPage", "/view/admin/newsletter-manager.jsp");
            request.getRequestDispatcher("/layout/admin-main.jsp").forward(request, response);
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