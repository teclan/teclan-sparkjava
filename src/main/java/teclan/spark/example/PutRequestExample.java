package teclan.spark.example;

import static spark.Spark.put;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teclan.spark.example.model.Person;
import teclan.utils.GsonUtils;

public class PutRequestExample {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PutRequestExample.class);

    public PutRequestExample() {

        put("/books", (request, response) -> {

            return "Book updated";
        });

        put("/update/:id", (request, response) -> {

            Person person = GsonUtils.fromJson(request.body(), Person.class);

            person.update("new-name", 20, "new-sex");

            return GsonUtils.toJson(person);
        });
    }

    public static void init() {
        new PutRequestExample();

    }

}
