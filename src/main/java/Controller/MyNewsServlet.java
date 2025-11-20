package Controller;

import Dao.CategoryDAO;
import Dao.NewsDAO;
import Model.News;
import Model.User;
import Util.UploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/my-news")
@MultipartConfig
public class MyNewsServlet extends HttpServlet {

    private final NewsDAO newsDAO = new NewsDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.isRole()) { // true = admin, false = phóng viên
            response.sendRedirect("login");
            return;
        }

        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            if (id != null) {
                try {
                    newsDAO.delete(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            response.sendRedirect("my-news");
            return;
        }

        if ("edit".equals(action)) {
            String id = request.getParameter("id");
            try {
                request.setAttribute("news", newsDAO.findById(id));
                request.setAttribute("listCate", categoryDAO.getAll());
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("contentPage", "/view/reporter/edit-news.jsp");
            request.getRequestDispatcher("/layout/main.jsp").forward(request, response);
            return;
        }

        // Trang danh sách tin của phóng viên
        try {
            request.setAttribute("listNews", newsDAO.getByAuthor(user.getId()));
            request.setAttribute("listCate", categoryDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("contentPage", "/view/reporter/my-news.jsp");
        request.getRequestDispatcher("/layout/main.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.isRole()) {
            response.sendRedirect("login");
            return;
        }

        Map<String, String> map = UploadUtil.processUpload("image", request);

        News n = new News();
        n.setId(map.get("id"));
        n.setTitle(map.get("title"));
        n.setContent(map.get("content"));

        String imagePath = map.get("image");
        if (imagePath == null || imagePath.isEmpty()) {
            imagePath = map.get("oldImage");
        }
        n.setImage(imagePath);

        n.setAuthor(user.getId());
        n.setCategoryId(map.get("categoryId"));
        n.setHome("on".equals(map.get("home")));

        try {
            if (n.getId() != null && !n.getId().isEmpty()) {
                newsDAO.update(n);
            } else {
                n.setId(newsDAO.generateNewsId());
                newsDAO.insert(n);
            }
            response.sendRedirect("my-news");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi lưu tin!");
            doGet(request, response);
        }
    }
}