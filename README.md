
使用 sparkjava 实现简单的后台服务,通过自定义的接口,快速实现后台服务的创建和实现

框架官网:http://sparkjava.com/

GitHub项目主页:https://github.com/perwendel/spark

RestApiService 提供如下接口:

//指定url,一个用来处理此服务的 Handle 实例, url的参数列表,即可创建一个后台服务,
//只需要关系url和参数配置,最终请求的所有参数都封装成一个Map<String,String>对象
//传递给 Handle 实例
createGetRequestService(String url, Handle handle, String... paramaters)

//post 请求中url不需要设置参数,参数会在前台发起请求试自带,自带的参数都封装成一个Map<String,String>对象
//传递给 Handle 实例
createPostRequestService(String url, Handle handle)


//Put 请求除了指定url和参数之外,需要指定一个继承自 declean.spark.model.AbstractServiceModel
//的子类,put请求的参数最终会封装成为指定类的一个对象(要求参数要匹配,否则封装失败),最后Handle再去
//参数封装后的对象,调用的是对象的 handle()方法,根据需要自定义 handle()
public <T> void createPutRequestService(String url, Handle handle,
            Class<? extends AbstractServiceModel> classOfT,
            String... paramaters)
            
 
//Put这个方法比上面的更加灵活,可以指定调用那个类的哪个方法来处理请求
createPutRequestService(String url, Handle handle,
            String method, Class<T> classOfT, String... paramaters)


// Post 文件上传 Handle 处理前台上传的文件
 public void createUploadRequestService(String url, Handle handle,
            String part)












