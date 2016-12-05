package teclan.spark;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Service;
import teclan.spark.example.DeleteRequestExample;
import teclan.spark.example.GetRequestExample;
import teclan.spark.example.PostRequestExample;
import teclan.spark.example.PutRequestExample;

public class Main {
	private static final Logger LOGGER =LoggerFactory.getLogger(Main.class);

    public static final int PORT = 3770;

    private static Service  service;

    public static void start() {

        service = Service.ignite();

        // 使用 https
        // secure("deploy/keystore.jks", "password", null, null);

    

        port(PORT);
        
        // 必须使用 import static spark.Spark.staticFiles 的 staticFiles 来加载静态文件
        // 纯文本文件会直接在浏览器显示，带有特定格式的会被下载
        staticFiles.location("public"); 
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
