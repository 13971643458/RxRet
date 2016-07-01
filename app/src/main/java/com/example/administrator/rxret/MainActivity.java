package com.example.administrator.rxret;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.rxret.javabean.RequestBean;
import com.example.administrator.rxret.javabean.ResponseBean;
import com.example.administrator.rxret.javabean.ResponseBody;
import com.example.administrator.rxret.request.RApiService;
import com.example.administrator.rxret.utils.RRetrofit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        button=(Button)findViewById(R.id.bt_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /****              Retrofit使用              ****/
    private void get() {
        RApiService service = RRetrofit.create(RApiService.class);
        /*get请求*/
        Call<ResponseBean> api = service.getApi("pa1_value", "ba1_value");
        api.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.v("INFO","get-->\n" + response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.e("error","get error-->\n" + t.toString());
            }
        });
    }

    private void getMap(){
        Map<String, String> params = new HashMap<>();
        params.put("bbb", "bbb_v");
        params.put("aaa", "aaa_v");
        RApiService service = RRetrofit.create(RApiService.class);
        Call<ResponseBody> apiString = service.getApiString(params);
        apiString.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("INFO","getMap-->\n" + response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error","getMap error-->\n" + t.toString());
            }
        });
    }

    private void postJavaBean(){
        RApiService service = RRetrofit.create(RApiService.class);
        RequestBean requestBean=new RequestBean();
        requestBean.key1 = "KEY1";
        requestBean.key2 = "KEY2";
        requestBean.key3 = "KEY3";
        requestBean.key4 = "KEY4";
        Call<ResponseBean> postApi = service.postApi(requestBean);
        postApi.enqueue(new Callback<ResponseBean>() {
            @Override
            public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                Log.v("INFO","post-->\n" + response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBean> call, Throwable t) {
                Log.e("error","post error-->\n" + t.toString());
            }
        });
    }

    private void postString(){
        RApiService service = RRetrofit.create(RApiService.class);
        RequestBean requestBean=new RequestBean();
        requestBean.key1 = "KEY1";
        requestBean.key2 = "KEY2";
        requestBean.key3 = "KEY3";
        requestBean.key4 = "KEY4";
        Call<ResponseBody> postApi = service.postApiString(requestBean);
        postApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("INFO","postString-->\n" + response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error","postString error-->\n" + t.toString());
            }
        });
    }

    private void upload(){

        try {
            RequestBody staffPhone = RequestBody.create(MediaType.parse("text/plain"), "13971643458");
            RequestBody time = RequestBody.create(MediaType.parse("text/plain"), "时间");
            RequestBody address = RequestBody.create(MediaType.parse("text/plain"), "地点");
            RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "类型");

            Map<String, RequestBody> map = new HashMap<>();
            map.put("staffPhone",staffPhone);
            map.put("checkTime",time);
            map.put("checkAddress",address);
            map.put("checkType",type);

            File imgFile=new File("");
            if (imgFile != null) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), imgFile);
                map.put("image\"; filename=\""+imgFile.getName()+"", fileBody);
            }

            RApiService service = RRetrofit.create(RApiService.class);

            service.checkIn(new RequestBody() {
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****      RxJava的使用       ****/
    private void getRxApiString(){
        RApiService service = RRetrofit.create(RApiService.class);
        Map<String, String> params = new HashMap<>();
        params.put("bbb", "bbb_v");
        params.put("aaa", "aaa_v");
        service.getRxApiString(params).subscribeOn(Schedulers.newThread())/*.observeOn(AndroidSchedulers.mainThread())*/.subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.v("INFO","getRxApiString onCompleted-->\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error","getRxApiString error-->\n" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    Log.v("INFO","getRxApiString onNext-->\n" + responseBody.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postRxApiString(){
        RApiService service = RRetrofit.create(RApiService.class);
        RequestBean requestBean=new RequestBean();
        service.postRxApiString(requestBean).subscribeOn(Schedulers.newThread())/*.observeOn(AndroidSchedulers.mainThread())*/.subscribe(new Subscriber<ResponseBean>() {
            @Override
            public void onCompleted() {
                Log.v("INFO","postRxApiString onCompleted-->\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error","postRxApiString error-->\n" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBean responseBean) {
                try {
                    Log.v("INFO","postRxApiString onNext-->\n" + responseBean.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
