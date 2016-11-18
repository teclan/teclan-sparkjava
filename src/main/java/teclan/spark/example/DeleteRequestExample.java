package teclan.spark.example;

import static spark.Spark.delete;

import us.monoid.json.JSONObject;

public class DeleteRequestExample {

    public DeleteRequestExample() {
        delete("/logout/:id", (request, response) -> {
            // TO DO
            return new JSONObject().toString();
        });
    }

    public static void init() {
        new DeleteRequestExample();
    }

}
