package buiduyanh.fpolyhn.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import buiduyanh.fpolyhn.retrofit.adapter.ProductAdapter;
import buiduyanh.fpolyhn.retrofit.interfaces.ProductInterfave;
import buiduyanh.fpolyhn.retrofit.model.Product;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText edtName,edtPrice,edtBrand;
    private Button btnOK;
    private RecyclerView rycProduct;

    private ProductAdapter adapter;
    private ArrayList<Product> list = new ArrayList<>();

    public static final String URL_API="http://10.82.1.155:3000/apiProduct/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getListProduct();
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtBrand = findViewById(R.id.edtBrand);

        rycProduct = findViewById(R.id.RecyclerProduct);
        btnOK = findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void addProduct() {
        String name = edtName.getText().toString();
        String price = edtPrice.getText().toString();
        String brand = edtBrand.getText().toString();

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProductInterfave productInterface = retrofit.create(ProductInterfave.class);
        Call<Product> call = productInterface.addProduct(new Product(name, price, brand));
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Add thanh cong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    private void getListProduct() {
        //Tao doi tuong Gson
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        //sử dụng ProductInterface
        ProductInterfave productInterface = retrofit.create(ProductInterfave.class);
        Call<List<Product>> call = productInterface.getListProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Log.d("response.body", response.body().toString());
                    list.clear();
                    list.addAll(response.body());
                    adapter = new ProductAdapter(MainActivity.this, list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    rycProduct.setLayoutManager(linearLayoutManager);
                    rycProduct.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}