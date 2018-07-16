package com.example.hp.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hp.task.Adapter.ProductAdapter;
import com.example.hp.task.Data.Constant;
import com.example.hp.task.Data.EndlessRecyclerViewScroll;
import com.example.hp.task.Data.MySingleton;
import com.example.hp.task.Model.Image;
import com.example.hp.task.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView gridView;
    ProductAdapter productAdapter;
    StringRequest stringRequest;
    Product product;
    ArrayList<Product> productList;
    Image image;
    JSONArray jsonArray;
    JSONObject productobjec;
    JSONObject imageobject;
    LinearLayout bottomBar;
    ProgressBar progressBar;
    static int pagno = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridproducts);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        gridView.setLayoutManager(llm);
        gridView.setHasFixedSize(true);
        bottomBar = findViewById(R.id.bottomBar);
        progressBar = findViewById(R.id.loading_bar);
        productList = new ArrayList<>();

        gridView.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                pagno += 10;
                getproduct(pagno);

            }
        });

        getproduct(pagno);
        setTitle("Products");







    }
    private void getproduct (int page){
        stringRequest = new StringRequest(Request.Method.GET, Constant.ECOMMERCE_URL + "products?count=10&from=" +page
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    jsonArray = new JSONArray(response);


                    for( int i=0; i <jsonArray.length(); i++){
                        productobjec = jsonArray.getJSONObject(i);
                        imageobject = productobjec.getJSONObject("image");
                        image = new Image(productobjec.getInt("id"),
                                imageobject.getInt("width"), imageobject.getInt("height"), imageobject.getString("url") );
                        product = new Product(productobjec.getInt("id") ,
                                productobjec.getString("productDescription"),
                                productobjec.getString("price")+"$" ,image);
                        productList.add(product);
                    }
                    productAdapter = new ProductAdapter(MainActivity.this , productList);
                    gridView.setAdapter(productAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

    }
}
