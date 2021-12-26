package com.imtiaz.quotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.app.ProgressDialog;
import android.os.Bundle;
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
    ProgressDialog progressDialog;
    SearchView search_View;
    ArrayList<QuotesModel> quotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_View = findViewById(R.id.search_View);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching The Quotes ....");
        progressDialog.show();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        processdata();

        search_View.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<QuotesModel> filteredList = new ArrayList<>();

        for(QuotesModel item : git){
            if(item.getText().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
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
                progressDialog.dismiss();
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<QuotesModel>> call, Throwable t) {

            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        MenuItem item=menu.findItem(R.id.action_search);
//
//        SearchView searchView=(SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText)
//            {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }


}