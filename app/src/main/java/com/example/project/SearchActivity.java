package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.project.data.model.Product;
import com.example.project.utils.CallBackUtil;
import com.example.project.utils.OkhttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;

public class SearchActivity extends AppCompatActivity {
    private Gson gson;
    private List<String> mStrs = new ArrayList<> ();
    private ArrayAdapter adapter;
    private SearchView mSearchView;
    private ListView mListView;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = findViewById(R.id.searchView);
        mListView = findViewById(R.id.listView);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // This method is used when the search view is clicked
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // This method is used when the search content changes
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    //
                    String url = "http://10.0.2.2:8080/search?term=" + newText;
                    OkhttpUtil.okHttpGet(url, new CallBackUtil.CallBackString() {
                        @Override
                        public void onFailure(Call call, Exception e) {
                            Toast.makeText(SearchActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
                        }

                        @TargetApi(Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(String response) {
                            // convert json to object
                            if (gson == null) {
                                gson = new GsonBuilder().create();
                            }
                            products = gson.fromJson(response, new TypeToken<List<Product>>() {}.getType());

                            List<String> productParams = products.stream().map(Product -> Product.getName()+"       address:"+
                                    Product.getAddress()).collect(Collectors.toList());
                            productParams.forEach(String -> {
                                if (!mStrs.contains(String)) {
                                    mStrs.add(String);
                                }
                            });

                            if (adapter == null) {
                                adapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_list_item_1, mStrs);
                            }
                            mListView.setAdapter(adapter);
                            mListView.setTextFilterEnabled(true);
                            adapter.getFilter().filter(newText);
                        }
                    });
                } else {
                    mListView.setAdapter(null);
                    mListView.clearTextFilter();
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(
            (AdapterView<?> adapterView, View view, int i, long l) -> {
                Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("productInfo", gson.toJson(products.get(i)));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        );
    }
}