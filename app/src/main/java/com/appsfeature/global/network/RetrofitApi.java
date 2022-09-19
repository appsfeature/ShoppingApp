package com.appsfeature.global.network;

import com.appsfeature.global.model.Relative_Product_Model_res;
import com.appsfeature.global.model.Relative_data_Product;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApi
{
    @Headers("Content-Type: application/json")
    @POST("getappsimilarproduct")
    Call<Relative_Product_Model_res> createPost(@Body String body);
        //on below line we are creating a method to post our data.

}
