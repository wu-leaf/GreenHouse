package cn.edu.gdou.www.greenhouse.http;



import java.util.Map;


import cn.edu.gdou.www.greenhouse.data.BigItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Veyron on 2017/10/15.
 * Function：
 */

public interface Api {
//http://120.76.205.241:8000/news/
    @GET("qihoo")
    Call<BigItem> listNewsItem2(@QueryMap Map<String, String> map);
}
