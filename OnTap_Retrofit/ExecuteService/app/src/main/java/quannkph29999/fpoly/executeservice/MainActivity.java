package quannkph29999.fpoly.executeservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import quannkph29999.fpoly.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ExecutorService service;
    ProductAdapter productAdapter;
    EditText name, price, brand,search;
    Button addproduct,btn_search;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.edt_addname);
        price = findViewById(R.id.edt_addprice);
        brand = findViewById(R.id.edt_addbrand);
        addproduct = findViewById(R.id.btn_addproduct);
        search = findViewById(R.id.edt_searchbyid);
        btn_search = findViewById(R.id.btn_searchproduct);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(productAdapter);

        service = Executors.newCachedThreadPool();
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty() || price.getText().toString().isEmpty() || brand.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    Product newproduct = new Product();
                    newproduct.setName(name.getText().toString());
                    newproduct.setPrice(Integer.parseInt(price.getText().toString()));
                    newproduct.setBrand(brand.getText().toString());
                    ApiService.apiservice.AddProduct(newproduct).enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                price.setText("");
                                brand.setText("");
                                fetchData();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {

                        }
                    });

                }


            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString().isEmpty()){
                    fetchData();
                }
                else {
                    ApiService.apiservice.GetProductById(search.getText().toString()).enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            Product newProduct = response.body();
                            List<Product> newData = new ArrayList<>();
                            if(newProduct != null){
                                newData.add(newProduct);
                                productAdapter.setDataProduct(newData);

                            }
                            else {
                                Toast.makeText(MainActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Lỗi khi tìm kiếm sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        fetchData();
    }

    public void fetchData() {
        ApiService.apiservice.getDataProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> newData = response.body();
                    productAdapter.setDataProduct(newData);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}