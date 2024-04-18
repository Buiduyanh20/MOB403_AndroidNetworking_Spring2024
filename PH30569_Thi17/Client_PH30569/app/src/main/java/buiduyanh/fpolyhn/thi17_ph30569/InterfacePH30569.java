package buiduyanh.fpolyhn.thi17_ph30569;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfacePH30569 {

    public class Constants {
        public static final String BASE_URL = "http://10.24.8.54:3000/";
    }
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create();
    InterfacePH30569 apiservice = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(InterfacePH30569.class);

    @GET("item/get")
    Call<List<NhanVienPH30569>> getList();

    @GET("item/get/{id}")
    Call<NhanVienPH30569> getListById(@Path("id") String id);

    @POST("item/post")
    Call<NhanVienPH30569> addProduct(@Body NhanVienPH30569 item);

    @PUT("item/update/{id}")
    Call<NhanVienPH30569> updateProduct(@Path("id") String productId, @Body NhanVienPH30569 item);

    @DELETE("item/delete/{id}")
    Call<NhanVienPH30569> deleteProduct(@Path("id") String id);
}
