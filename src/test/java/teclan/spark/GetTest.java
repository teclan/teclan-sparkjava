package teclan.spark;

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
public class GetTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetTest.class);

    private static final String WITHOUT_PARAMETERS_GET_URL     = "http://localhost:%d/hello";
    private static final String WITH_PARAMETERS_GET_URL        = "http://localhost:%d/hello/%s";
    private static final String WITH_MULTIL_PARAMETERS_GET_URL = "http://localhost:%d/say/%s/to/%s";

    @Test
    public void withoutParamaters() {
        try {

            LOGGER.info("\nwithout parameters :" + new Resty().text(
                    String.format(WITHOUT_PARAMETERS_GET_URL, Main.PORT)));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void withParamaters() {
        try {

            LOGGER.info("\nwith parameters :" + new Resty().text(
                    String.format(WITH_PARAMETERS_GET_URL, Main.PORT, "燃灯")));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void withMultilParameters() {
        try {

            LOGGER.info("\nwith multil parameters :" + new Resty()
                    .text(String.format(WITH_MULTIL_PARAMETERS_GET_URL,
                            Main.PORT, "万岁", "燃灯")));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
