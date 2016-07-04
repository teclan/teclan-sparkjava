package teclan.spark.service;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Request;
import teclan.spark.model.AbstractServiceModel;
import teclan.spark.service.handle.Handle;
import teclan.spark.utils.GsonUtils;

public class DefaultRestApiService implements RestApiService {

    private final static Logger LOGGER = LoggerFactory
            .getLogger(RestApiService.class);

    // private static final String ROOT_URL = "/api/v1%s";
    private static final String ROOT_URL = "%s";

    /**
     * @author Teclan
     * 
     *         添加一个 GET 请求服务, url跟在基础链接后,例如:
     * 
     *         基础链接: localhost:3770
     * 
     *         url: /get-info
     * 
     *         paramaters : "name","age"
     * 
     *         则完整的URL:http://localhost:3770/get-info/:name/:age
     * 
     *         发起请求:http://localhost:3770/get-info/Teclan/24
     * 
     *         则有 : ["name":"Teclan","age":"24"]
     * 
     * 
     * @param url
     *            请求url
     * 
     * @param handle
     *            处理的接口
     * 
     * @param paramaters
     * 
     *            参数列表(可选)
     */
    @Override
    public void createGetRequestService(String url, Handle handle,
            String... paramaters) {

        LOGGER.info("GET Add route {} ", generatorUrl(url, paramaters));

        get(generatorUrl(url, paramaters), (request, response) -> {

            String body = handle
                    .handle(getParamatersAndValues(request, paramaters));

            if (handle.status(200)) {

            } else {
                response.status(handle.getStatus());
            }

            response.body(body);

            return response.body();

        });

    }

    /**
     * @author Teclan
     * 
     *         添加一个 Post 请求服务
     * 
     * @param url
     *            请求url
     * 
     * @param handle
     *            处理的接口
     */
    @Override
    public void createPostRequestService(String url, Handle handle) {

        LOGGER.info("POST Add route {} ", generatorUrl(url));

        post(generatorUrl(url), (request, response) -> {

            String body = handle.handle(getParamatersAndValues(request.body()));

            if (handle.status(200)) {

            } else {
                response.status(handle.getStatus());
            }

            response.body(body);

            return response.body();

        });

    }

    /**
     * @author Teclan
     * 
     *         添加一个文件上传(Post)请求服务, 前端form表单需要设置 enctype='multipart/form-data'
     * 
     * @param url
     *            请求url
     * 
     * @param handle
     *            处理的接口
     * 
     * @param part
     *            前端文件上传控件域的"name"属性值,必须保持一直,例如: < input type='file'
     *            name='uploaded_file' >, 则 part 的值应该为 "uploaded_file"
     */
    @Override
    public void createUploadRequestService(String url, Handle handle,
            String part) {

        LOGGER.info("(upload)POST Add route {} ", generatorUrl(url));

        post(generatorUrl(url), (request, response) -> {

            request.attribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement("/temp"));

            try (InputStream input = request.raw().getPart(part)
                    .getInputStream()) {

                String body = handle.handle(input,
                        request.raw().getPart(part).getSubmittedFileName());

                if (handle.status(200)) {

                } else {
                    response.status(handle.getStatus());
                }

                response.body(body);

                return response.body();

            }

        });

    }

    /**
     * @author Teclan
     * 
     *         添加一个PUT 请求服务,指定一个类(继承
     *         declean.spark.model.AbstractServiceModel)处理具体请求,
     *         put请求的参数全部转换成子类的对象,具体的处理逻辑在子类 handle() 方法中实现,
     * 
     *         url跟在基础链接后,例如:
     * 
     *         基础链接: localhost:3770
     * 
     *         url: /update
     * 
     *         paramaters : "id"
     * 
     *         则完整的URL:http://localhost:3770/update/:id
     * 
     *         发起请求:http://localhost:3770/update/70
     * 
     *         则有 : ["id":"70"]
     * 
     *         请求参数会被包装成指定类的对象
     * 
     * 
     * @param url
     *            请求url
     * 
     * @param handle
     *            处理的接口
     * 
     * @param classOfT
     *            指定处理此请求的类(此类继承 declean.spark.model.AbstractServiceModel)
     * 
     * @param paramaters
     * 
     *            参数列表(可选)
     */
    @Override
    public <T> void createPutRequestService(String url, Handle handle,
            Class<? extends AbstractServiceModel> classOfT,
            String... paramaters) {

        LOGGER.info(" PUT Add route {} ", generatorUrl(url, paramaters));

        put(generatorUrl(url, paramaters), (request, response) -> {

            Object object = GsonUtils.fromJson(request.body(), classOfT);

            String body = handle.handle(object);

            if (handle.status(200)) {

            } else {
                response.status(handle.getStatus());
            }

            response.body(body);

            return response.body();
        });

    }

    /**
     * @author Teclan
     * 
     *         添加一个PUT 请求服务,指定一个类(继承
     *         declean.spark.model.AbstractServiceModel)处理具体请求,
     *         put请求的参数全部转换成子类的对象,具体的处理逻辑在子类中指定的方法(method参数)中实现,
     * 
     *         url跟在基础链接后,例如:
     * 
     *         基础链接: localhost:3770
     * 
     *         url: /update
     * 
     *         paramaters : "id"
     * 
     *         则完整的URL:http://localhost:3770/update/:id
     * 
     *         发起请求:http://localhost:3770/update/70
     * 
     *         则有 : ["id":"70"]
     * 
     *         请求参数会被包装成指定类的对象
     * 
     * 
     * @param url
     *            请求url
     * 
     * @param handle
     *            处理的接口
     * 
     * @param method
     * 
     *            指定类的方法名,调用此方法处理请求逻辑
     * 
     * @param classOfT
     *            指定处理此请求的类(此类继承 declean.spark.model.AbstractServiceModel)
     * 
     * @param paramaters
     * 
     *            参数列表(可选)
     */
    @Override
    public <T> void createPutRequestService(String url, Handle handle,
            String method, Class<T> classOfT, String... paramaters) {

        LOGGER.info(" PUT Add route {} ", generatorUrl(url, paramaters));

        put(generatorUrl(url, paramaters), (request, response) -> {

            Object object = GsonUtils.fromJson(request.body(), classOfT);

            String body = handle.handle(object, method);

            if (handle.status(200)) {

            } else {
                response.status(handle.getStatus());
            }

            response.body(body);

            return response.body();
        });

    }

    @Override
    public void delete() {
        // TODO Auto-generated method stub

    }

    private static String getSubUrl(String... paramaters) {

        StringBuilder subUrl = new StringBuilder();

        for (String paramater : paramaters) {

            if ("*".equals(paramater)) {
                continue;
            }

            subUrl.append(String.format("/:%s", paramater));

        }

        return subUrl.toString();

    }

    private static String generatorUrl(String url, String... paramaters) {

        String subUrl = getSubUrl(paramaters);

        if (subUrl == null) {
            return String.format(ROOT_URL, url);
        }

        return String.format(ROOT_URL, url + subUrl);

    }

    protected ArrayList<String> getParamaters(String... paramaters) {
        ArrayList<String> list = new ArrayList<String>();

        for (String paramater : paramaters) {
            list.add(paramater);
        }
        return list;
    }

    protected Map<String, String> getParamatersAndValues(Request request,
            String... paramaters) {

        Map<String, String> map = new HashMap<String, String>();

        for (String paramater : paramaters) {
            map.put(paramater, request.params(paramater));
        }

        return map;
    }

    protected Map<String, String> getParamatersAndValues(String body) {

        Map<String, String> map = new HashMap<String, String>();

        String[] paramaters = body.split("&");

        for (String paramater : paramaters) {
            map.put(paramater.split("=")[0], paramater.split("=")[1]);
        }
        return map;
    }

}
