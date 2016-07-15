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

public class AttributeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PutTest.class);

    private static final String PUT_URL = "http://localhost:%d/attribute";

    @Test
    public void putTest() {

        Person person = new Person(70, "Teclan", "男");

        try {

            TextResource textResource = new Resty().text(
                    String.format(PUT_URL, Main.PORT),
                    put(new Content("application/json; charset=utf-8",
                            GsonUtils.toJson(person).getBytes("UTF-8"))));

            Person t = GsonUtils.fromJson(textResource.toString(),
                    Person.class);

            LOGGER.info(t.getName() + "\t" + t.getSex());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}
