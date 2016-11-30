package teclan.spark.example;

import static spark.Spark.get;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.monoid.json.JSONObject;

public class GetRequestExample {
    private final Logger LOGGER = LoggerFactory
            .getLogger(GetRequestExample.class);

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

                // // IE浏览器
                // filename = URLEncoder.encode(filename, "UTF-8");
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                response.raw().setHeader("Content-Disposition",
                        "attachment; filename=" + fileName);

                try (OutputStream outputStream = response.raw()
                        .getOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                                new FileInputStream(file))) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bufferedInputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, len);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            return new JSONObject();
        });

        get("/downloads/more", (request, response) -> {
            // filename 的值即为需要下载的文件
            try {
                String fileName = request.queryParams("filename");
                File file = new File(fileName);

                response.raw().setContentType("application/octet-stream");

                // // IE浏览器
                // filename = URLEncoder.encode(filename, "UTF-8");
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                response.raw().setHeader("Content-Disposition",
                        "attachment; filename=" + fileName + ".zip");

                try (ZipOutputStream zipOutputStream = new ZipOutputStream(
                        new BufferedOutputStream(
                                response.raw().getOutputStream()))) {

                    File file1 = new File("/home/dev/Downloads/中文11.doc");
                    File file2 = new File("/home/dev/Downloads/中文.doc");

                    List<File> files = new ArrayList<File>();
                    files.add(file1);
                    files.add(file2);

                    BufferedInputStream bufferedInputStream;
                    ZipEntry zipEntry;
                    for (File f : files) {
                        bufferedInputStream = new BufferedInputStream(
                                new FileInputStream(f));
                        zipEntry = new ZipEntry(
                                java.net.URLEncoder.encode(f.getName(), "GBK"));

                        zipOutputStream.putNextEntry(zipEntry);
                        zipOutputStream.setEncoding("UTF-8");
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bufferedInputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, len);
                        }
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
