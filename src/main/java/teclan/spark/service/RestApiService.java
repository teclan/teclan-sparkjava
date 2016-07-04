package teclan.spark.service;

import teclan.spark.model.AbstractServiceModel;
import teclan.spark.service.handle.Handle;

public interface RestApiService {

    public void createGetRequestService(String url, Handle handle,
            String... paramaters);

    public void createPostRequestService(String url, Handle handle);

    public void createUploadRequestService(String url, Handle handle,
            String part);

    public <T> void createPutRequestService(String url, Handle handle,
            Class<? extends AbstractServiceModel> classOfT,
            String... paramaters);

    public <T> void createPutRequestService(String url, Handle handle,
            String method, Class<T> classOfT, String... paramaters);

    public void delete();

}
