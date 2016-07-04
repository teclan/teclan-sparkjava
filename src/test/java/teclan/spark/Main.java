package teclan.spark;

import static spark.Spark.port;

import spark.Service;
import teclan.spark.factory.GetFactory;
import teclan.spark.factory.PostFactory;
import teclan.spark.factory.PutFactory;

public class Main {

    public static final int PORT = 3770;

    private static Service service;

    public static void start() {

        service = Service.ignite();

        // service.staticFiles
        // .externalLocation(System.getProperty("java.io.tmpdir"));

        port(PORT);

        GetFactory.init();
        PostFactory.init();
        PutFactory.init();
    }

    public static void stop() {
        service.stop();
    }

    public static void main(String[] args) {
        start();
    }

}
