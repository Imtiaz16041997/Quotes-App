package com.imtiaz.quotes.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private static final String url = "https://type.fit/api/";
    private static ApiController controller;
    private static Retrofit retrofit;

    ApiController(){
        //Retrofit Object Creation
         retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiController getInstance()
    {
        if(controller== null){
            controller = new ApiController();
        }
            return controller;
    }

        public quotesApi getApi(){
         return  retrofit.create(quotesApi.class);
        }

}
