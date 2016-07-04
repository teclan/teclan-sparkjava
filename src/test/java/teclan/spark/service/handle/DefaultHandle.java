package teclan.spark.service.handle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.spark.service.handle.Handle;

public class DefaultHandle implements Handle {
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultHandle.class);

    private int status = 200;;

    @Override
    public String handle(Map<String, String> paramatersAndValues) {

        show(paramatersAndValues);

        setStatus(401);

        return "身份认证错误";

    }

    @Override
    public <T> String handle(Object object) {

        return this.handle(object, "handle");

    }

    @Override
    public <T> String handle(Object object, String method) {

        Class<?> cls = object.getClass();

        String s = "";
        try {
            Method actualMethod = cls.getDeclaredMethod(method);
            s = (String) actualMethod.invoke(object);
        } catch (Exception e) {

            setStatus(500);
        }

        return "call the method in " + object.getClass() + s;
    }

    @Override
    public String handle(InputStream input, String originPath) {

        File uploadDir = new File("upload");
        uploadDir.mkdir();

        String filePath = uploadDir.getAbsolutePath() + File.separator
                + originPath;

        try {
            Files.copy(input, new File(filePath).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            return "文件上传处理完毕";

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);

            setStatus(500);

            return "文件上传处理异常" + e;
        }

    }

    private void show(Map<String, String> paramatersAndValues) {

        for (String key : paramatersAndValues.keySet()) {

            LOGGER.info("\nkey:{} values:{}", key,
                    paramatersAndValues.get(key));
        }
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean status(int status) {

        return this.status == status;
    }

}
