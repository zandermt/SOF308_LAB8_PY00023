package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@CrossOrigin(origins = "http://localhost:8081")
@WebServlet("/api/upload")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Fixed upload directory - update this to your project's image upload path
    private static final String UPLOAD_DIRECTORY = "C:\\CoDiNg4FuN\\VueJS\\sof308-assignment-py00023\\public\\images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Check if the request is multipart
            if (request.getContentType() == null || 
                !request.getContentType().toLowerCase().startsWith("multipart/form-data")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("{\"error\": \"Form data is not multipart\"}");
                return;
            }

            // Ensure upload directory exists
            File uploadDir = new File(UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Process file upload
            Part filePart = request.getPart("images");
            if (filePart != null && filePart.getSize() > 0) {
                // Get original filename
                String fileName = filePart.getSubmittedFileName();
                
                // Create file path
                File file = new File(UPLOAD_DIRECTORY, fileName);

                // Save file
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(filePart.getInputStream().readAllBytes());
                }

                // Prepare response with relative path
                String relativeFilePath = "images/" + fileName;
                
                // Create JSON response
                ObjectMapper mapper = new ObjectMapper();
                String jsonResponse = mapper.writeValueAsString(
                    Map.of("imageUrl", relativeFilePath)
                );

                // Send response
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("{\"error\": \"No file uploaded\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("{\"error\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}