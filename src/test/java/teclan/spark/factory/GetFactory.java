package teclan.spark.factory;

import static spark.Spark.get;

public class GetFactory {

    public GetFactory() {
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
            return "<form  action=\"http://localhost:3770/upload\" method='post' enctype='multipart/form-data'>"
                    // + " <input type='file' name='uploaded_file'
                    // accept='.png'>"
                    + "    <input type='file' name='uploaded_file' >"
                    + "    <button>Upload picture</button>" + "</form>";
        });

    }

    public static void init() {
        new GetFactory();

    }

}
