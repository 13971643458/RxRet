package com.example.administrator.rxret.request;

import com.example.administrator.rxret.javabean.RequestBean;
import com.example.administrator.rxret.javabean.ResponseBean;
import com.example.administrator.rxret.javabean.ResponseBody;
import com.example.administrator.rxret.javabean.ResponseCode;
import com.google.gson.JsonElement;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/1.
 */
public interface RApiService {

    /**
     * @GET 表明方法是 get请求
     * "/api" 请求的接口,请注意前面的/符号
     * @Query 表示这是一个参数
     * Call<ResponseBody> :Call是必须的,ResponseBody是Retrofit默认的返回数据类型,也就是String体
     */

    @GET("/api")
    Call<ResponseBean> getApi(@Query("pa1") String va1, @Query("ba1") String va2);
    //getApi方法,等效于: http://192.168.1.12:8082/api?pa1=va1&ba1=va2

    /**
     * @POST 请求方式post
     * @Body 表示将requestBean对象转成成json string作为参数传递给后台
     */
    @POST("/api")
    Call<ResponseBean> postApi(@Body RequestBean requestBean);

    /**
     * @QueryMap 表示将map类型的params对象, 转成键值对的形式作为参数传递给后台
     */
    @GET("/api")
    Call<ResponseBody> getApiString(@QueryMap Map<String, String> params);

    @POST("/api")
    Call<ResponseBody> postApiString(@Body RequestBean requestBean);

    @Multipart
    @POST("/user/addLicenseInfo")
    //typedfile
    void addLicenseInfo(@QueryMap Map<String, Object> options, @Part("file") File file, Callback<JsonElement> response);


    @Multipart
    @POST("/check/checkin.action")
    /**
     * 上传图片封装的方法
     * RequestBody这个是请求的参数实体
     * ResponseCode这个是返回的实体类
     */
    Call<ResponseCode> checkIn(@Part("image\"; filename=\"文件名.jpg") RequestBody file);


    @Multipart
    @POST("/upload")
//    @Headers()
    Call<String> uploadImage(@Part("fileName") String description, @Part("file\"; filename=\"image.png\"")RequestBody ...imgs);

    /**
     * 上传三张图片
     * @param description
     * @param imgs
     * @param imgs1
     * @param imgs3
     * @return
     */
    @Multipart
    @POST("/upload")
    Call<String> uploadImage(@Part("fileName") String description,
                             @Part("file\"; filename=\"image.png\"")RequestBody imgs,
                             @Part("file\"; filename=\"image.png\"")RequestBody imgs1,
                             @Part("file\"; filename=\"image.png\"")RequestBody imgs3);


    /*******   RxJava   *******/
    /**
     * Observable 是RxJava的关键点,其他不变
     */
    @GET("/api")
    Observable<ResponseBody> getRxApiString(@QueryMap Map<String, String> params);

    @POST("/api")
    Observable<ResponseBean> postRxApiString(@Body RequestBean requestBean);
}
