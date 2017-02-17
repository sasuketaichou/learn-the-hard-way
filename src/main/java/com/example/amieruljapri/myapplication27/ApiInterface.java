package com.example.amieruljapri.myapplication27;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by amierul.japri on 12/19/2016.
 */

public interface ApiInterface {

    @GET("rest.php")
    Call<GlpiLogin> login(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<PojoCountTicketStatus> getcountTicketStatus(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<PojoCreateTicketValues> createTicketValues(@QueryMap Map<String, String> params);

    @GET("rest.php")
    Call<PojoMyInfo> getMyInfo(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<List<PojoTypeUrgencyValues>> getTypeUrgencyValues(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<List<PojoHelpdeskValues>> getHelpdeskValues(@QueryMap Map<String,String> params);

    @GET("rest.php")
    Call<List<PojoListTicketsValues>> getListticketValues(@QueryMap Map<String,String> params);

}
