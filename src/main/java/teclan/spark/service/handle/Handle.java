package teclan.spark.service.handle;

import java.io.InputStream;
import java.util.Map;

public interface Handle {

    public int getStatus();

    public boolean status(int status);

    public String handle(Map<String, String> paramataersAndValues);

    public <T> String handle(Object object);

    public <T> String handle(Object object, String method);

    public String handle(InputStream input, String originPath);

}
