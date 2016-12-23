package com.example.amieruljapri.myapplication27;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by amierul.japri on 12/19/2016.
 */

public interface ApiInterface {

    @GET("rest.php")
    Call<GlpiLogin> login(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<List<PojoListDropdownValues>> getListDropdownValues(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<List<PojoHelpdeskValues>> getHelpdeskValues(@QueryMap Map<String,String> params);


}
