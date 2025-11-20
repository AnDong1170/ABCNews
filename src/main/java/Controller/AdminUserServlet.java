package Controller;

import Dao.UserDAO;
import Model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/user")
public class AdminUserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkAdmin(request, response);

        try {
            List<User> listUser = userDAO.getAll(); // anh gửi method getAll() ở cuối
            request.setAttribute("listUser", listUser);
            request.setAttribute("contentPage", "/view/admin/user-manager.jsp");
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