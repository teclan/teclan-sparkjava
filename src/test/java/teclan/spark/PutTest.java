package teclan.spark;

import static us.monoid.web.Resty.put;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.spark.model.Person;
import teclan.utils.GsonUtils;
import us.monoid.web.Content;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;

/**
 * 跑测试之前先 执行 teclean.spark.Main
 * 
 * @author Teclan (tbj621@gmail.com})
 *
 */
public class PutTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PutTest.class);

    private static final String PUT_URL = "http://localhost:%d/update1/%s";

    @Test
    public void putTest() {

        Person person = new Person(70, "Teclan", "男");

        try {

            TextResource textResource = new Resty().text(
                    String.format(PUT_URL, Main.PORT, person.getId()),
                    put(new Content("application/json; charset=utf-8",
                            GsonUtils.toJson(person).getBytes("UTF-8"))));

            LOGGER.info("{}", textResource.toString());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}
