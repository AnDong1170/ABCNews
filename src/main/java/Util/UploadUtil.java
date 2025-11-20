package Util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadUtil {
    public static Map<String, String> processUpload(String fieldName, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        try {
            File repository = new File(request.getServletContext().getRealPath("/" + Constant.UPLOAD_DIRECTORY));
            if (!repository.exists()) repository.mkdirs();

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(Constant.MAX_FILE_SIZE);
            upload.setSizeMax(Constant.MAX_REQUEST_SIZE);

            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                } else if (item.getFieldName().equals(fieldName) && item.getSize() > 0) {
                    String fileName = System.currentTimeMillis() + "_" + item.getName();
                    File uploadedFile = new File(repository, fileName);
                    item.write(uploadedFile);
                    map.put(fieldName, Constant.UPLOAD_DIRECTORY + "/" + fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}