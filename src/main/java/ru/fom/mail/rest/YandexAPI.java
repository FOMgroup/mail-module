package ru.fom.mail.rest;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import ru.fom.mail.entities.YandexResponse;

import java.util.Map;

public interface YandexAPI {

    @POST("/api2/admin/email/add")
    @FormUrlEncoded
    Call<Void> createMailbox(
            @Header("PddToken") String token,
            @FieldMap Map<String, String> request);


    @GET("/api2/admin/email/list")
    Call<YandexResponse> getAllMailboxes(@Header("PddToken") String token,
                                         @Query("domain") String domain,
                                         @Query("page") String page,
                                         @Query("on_page") String onPage);

    @POST("/api2/admin/email/edit")
    @FormUrlEncoded
    Call<Void> editMailbox(@Header("PddToken") String token,
                           @FieldMap Map<String, String> request);

    @POST("/api2/admin/email/del")
    @FormUrlEncoded
    Call<Void> deleteMailbox(@Header("PddToken") String token,
                             @Field("domain") String domain,
                             @Field("login") String email);

    public static final YandexAPI retrofit = new Retrofit.Builder()
            .baseUrl("https://pddimp.yandex.ru/")
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(YandexAPI.class);

}
