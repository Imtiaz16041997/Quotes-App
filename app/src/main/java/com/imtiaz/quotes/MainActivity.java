package com.imtiaz.quotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import com.imtiaz.quotes.adapter.QuotesAdapter;
import com.imtiaz.quotes.model.QuotesModel;
import com.imtiaz.quotes.retrofit.ApiController;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    QuotesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        processdata();
    }

    private void processdata() {

        Call<List<QuotesModel>> call = ApiController
                                        .getInstance()
                                        .getApi()
                                        .getQuotesModel();

        call.enqueue(new Callback<List<QuotesModel>>() {
            @Override
            public void onResponse(Call<List<QuotesModel>> call, Response<List<QuotesModel>> response) {

                List<QuotesModel> quotes = response.body();
                adapter = new QuotesAdapter((ArrayList<QuotesModel>) quotes,MainActivity.this);
                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<QuotesModel>> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName())
//        );
//
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processdata();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processdata();
//                return false;
//            }
//        });
//
//        return true;
//    }
}