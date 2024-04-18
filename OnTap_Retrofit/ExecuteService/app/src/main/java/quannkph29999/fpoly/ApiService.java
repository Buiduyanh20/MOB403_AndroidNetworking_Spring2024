package quannkph29999.fpoly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import quannkph29999.fpoly.executeservice.Product;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiservice = new Retrofit.Builder()
            .baseUrl("http://192.168.202.244:3000")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/getAll/product")
    Call<List<Product>> getDataProduct();

    @POST("/product/post")
    Call<Product> AddProduct(@Body Product product);

    @PUT("/editProduct/Product/{id}")
    Call<Product> EditProduct(@Path("id") String id, @Body Product product);

    @DELETE("/deleteProduct/{id}")
    Call<Product> DeleteProduct(@Path("id") String id);

    @GET("/getproduct/{id}")
    Call<Product> GetProductById(@Path("id") String id);

}
