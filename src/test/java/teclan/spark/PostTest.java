package teclan.spark;

import static us.monoid.web.Resty.form;

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
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PostTest.class);

    private static final String POST_URL = "http://localhost:%d/post";

    private static final String GOOGLE_QUERY_DATA = "q=Resty&hl=en&num=20";

    @Test
    public void postTest() {
        try {

            LOGGER.info("{}",
                    new Resty().text(String.format(POST_URL, Main.PORT),
                            form(GOOGLE_QUERY_DATA)).toString());

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

}
