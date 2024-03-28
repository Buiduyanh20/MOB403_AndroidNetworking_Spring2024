package buiduyanh.fpolyhn.retrofit.interfaces;

import java.util.List;

import buiduyanh.fpolyhn.retrofit.model.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductInterfave {
    @GET("getListProduct")
    Call<List<Product>> getListProduct();
    @POST("addProduct")
    Call<Product> addProduct(@Body Product PObj);
}
