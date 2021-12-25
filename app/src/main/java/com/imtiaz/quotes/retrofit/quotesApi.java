package com.imtiaz.quotes.retrofit;

import com.imtiaz.quotes.model.QuotesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface quotesApi {
    @GET("quotes")
    Call<List<QuotesModel>>  getQuotesModel();
}
