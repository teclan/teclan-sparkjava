package teclan.spark.example;

import static spark.Spark.get;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.monoid.json.JSONObject;

public class GetRequestExample {
    private final Logger LOGGER = LoggerFactory.getLogger(GetRequestExample.class);

    public GetRequestExample() {
        get("/hello", (req, res) -> "Hello World");

        // matches "GET /hello/foo" and "GET /hello/bar"
        // request.params(":name") is 'foo' or 'bar'
        get("/hello/:name", (request, response) -> {
            return "Hello: " + request.params(":name");
        });

        // matches "GET /say/hello/to/world"
        // request.splat()[0] is 'hello' and request.splat()[1] 'world'
        get("/say/*/to/*", (request, response) -> {
            return "Number of splat parameters: " + request.splat().length
                    + "\tparameters[0]:" + request.splat()[0]
                    + "\tparameters[1]:" + request.splat()[1];
        });

        get("/upload", (req, res) -> {
            // note the enctype
            // make sure to call getPart using the same "name" in the post
            return "<form  action=\"http://localhost:4200/api/v1/upload\" method='post' enctype='multipart/form-data'>"
                    // + " <input type='file' name='uploaded_file'
                    // accept='.png'>"
                    + "    <input type='file' name='uploaded_file' >"
                    + "    <button>Upload picture</button>" + "</form>";
        });

        get("/downloads", (request, response) -> {
            // filename 的值即为需要下载的文件
            try {
                String fileName = request.queryParams("filename");
                File file = new File(fileName);

                response.raw().setContentType("application/octet-stream");

                // NOTE
                // response.raw().setHeader("Content-Disposition",
                // "attachment; filename="+ fileName;
                // 将导致下载的压缩包如果含中文名时乱码，但是依然没有解决解压后的中文名乱码问题
                response.raw().setHeader("Content-Disposition",
                        "attachment; filename=" + java.net.URLEncoder
                                .encode(fileName + ".zip", "UTF-8"));

                try (ZipOutputStream zipOutputStream = new ZipOutputStream(
                        new BufferedOutputStream(
                                response.raw().getOutputStream()));
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                                new FileInputStream(file))) {

                    // // FIX ME
                    // // 这里存在中文文件名乱码的问题
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bufferedInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            return new JSONObject();
        });

    }

    public static void init() {
        new GetRequestExample();

    }

}
