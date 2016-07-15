package teclan.spark.factory;

import static spark.Spark.put;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.spark.model.Person;
import teclan.utils.GsonUtils;

public class PutFactory {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PutFactory.class);

    public void PutFactory() {

        put("/books", (request, response) -> {

            return "Book updated";
        });
    }

    public static void init() {
        /**
         * Note this
         * 
         * 直接 new PutFactory() put方法的路由不会被映射到
         */

        put("/update/:id", (request, response) -> {

            Person person = GsonUtils.fromJson(request.body(), Person.class);

            person.update("new-name", "new-sex");

            return "update id:" + request.params(":id")
                    + "\n,after update the info is :"
                    + GsonUtils.toJson(person);
        });

        put("/attribute", (request, response) -> {

            Person person = GsonUtils.fromJson(request.body(), Person.class);

            person.update("new-name", "new-sex");

            request.attribute("per", GsonUtils.toJson(person));

            response.body(request.attribute("per"));

            return response.body();

        });

    }

}
