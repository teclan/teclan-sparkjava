package teclan.spark.example;

import static spark.Spark.post;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;

public class PostRequestExample {

    public PostRequestExample() {

        post("/post", (request, response) -> "Hello World: " + request.body());

        post("/post/query",
                (request, response) -> "Hello World: "
                        + request.queryParams("fname") + " "
                        + request.queryParams("lname"));

        post("/upload", (req, res) -> {

            File uploadDir = new File("upload");
            // create the upload directory if it doesn't exist
            uploadDir.mkdir();

            req.attribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/temp"));

            String path = req.raw().getPart("uploaded_file")
                    .getSubmittedFileName();

            String filePath = uploadDir.getAbsolutePath() + File.separator
                    + path;

            try (InputStream input = req.raw().getPart("uploaded_file")
                    .getInputStream()) { // getPart needs to use same "name" as
                                         // input field in form
                Files.copy(input, new File(filePath).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

            }
            return "文件上传成功" + path;
        });
    }

    public static void init() {
        new PostRequestExample();
    }

}
