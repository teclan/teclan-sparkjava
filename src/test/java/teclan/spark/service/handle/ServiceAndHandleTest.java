package teclan.spark.service.handle;

import static spark.Spark.port;

import spark.Service;
import teclan.spark.model.Person;
import teclan.spark.service.DefaultRestApiService;
import teclan.spark.service.RestApiService;
import teclan.spark.service.handle.Handle;

public class ServiceAndHandleTest {

    private static Service service;

    private static Handle handle = new DefaultHandle();

    private static RestApiService restApiService = new DefaultRestApiService();

    public static void main(String[] args) {

        service = Service.ignite();

        port(3770);

        restApiService.createGetRequestService("/sina", handle, "name", "age");

        restApiService.createPutRequestService("/update", handle, Person.class,
                "id");

        restApiService.createPutRequestService("/update1", handle, "myString",
                Person.class, "id");

    }

}
