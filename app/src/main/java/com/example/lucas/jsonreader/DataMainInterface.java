package com.example.lucas.jsonreader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataMainInterface {
    String BASE_URL = "https://sheetlabs.com/TECN/";

    @GET("convidados?listaid=4532")
    Call<JsonArray> loadAllData(); // Método para obter o Json inteiro, que no caso é um JSON Array
    @GET("convidados?listaid=6862")
    Call<JsonArray> loadAllData2(); // Método para obter o Json inteiro, que no caso é um JSON Array
    @GET("convidados?listaid=6851")
    Call<JsonArray> loadAllData3(); // Método para obter o Json inteiro, que no caso é um JSON Array

}

