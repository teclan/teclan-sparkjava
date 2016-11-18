package teclan.spark;

import static spark.Spark.port;

import spark.Service;
import teclan.spark.example.DeleteRequestExample;
import teclan.spark.example.GetRequestExample;
import teclan.spark.example.PostRequestExample;
import teclan.spark.example.PutRequestExample;

public class Main {

    public static final int PORT = 3770;

    private static Service  service;

    public static void start() {

        service = Service.ignite();

        // 使用 https
        // secure("deploy/keystore.jks", "password", null, null);

        // 静态文件
        service.staticFiles
                .externalLocation(System.getProperty("java.io.tmpdir"));

        port(PORT);

        GetRequestExample.init();
        PostRequestExample.init();
        PutRequestExample.init();
        DeleteRequestExample.init();
    }

    public static void stop() {
        service.stop();
    }

    public static void main(String[] args) {
        start();
    }

}
