package teclan.spark.example.test;

import static us.monoid.web.Resty.form;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.monoid.web.Resty;

/**
 * 跑测试之前先 执行 teclean.spark.Main
 * 
 * @author Teclan (tbj621@gmail.com})
 *
 */
public class PostTest {
    private static final Logger LOGGER            = LoggerFactory
            .getLogger(PostTest.class);

    private static final String POST_URL          = "http://localhost:%d/api/v1/cases";

    private static final String GOOGLE_QUERY_DATA = "group_id=mg";

    @Test
    public void postTest() {
        try {

            LOGGER.info("{}", new Resty().text(String.format(POST_URL, 4200),
                    form(GOOGLE_QUERY_DATA)).toString());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    @Test
    public void delete() {
        try {
            new Resty().json(
                    "http://localhost:22501/api/v1/terminal-unregister/12345678910",
                    Resty.delete());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
