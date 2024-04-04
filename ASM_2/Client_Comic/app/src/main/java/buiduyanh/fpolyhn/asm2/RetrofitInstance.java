package buiduyanh.fpolyhn.asm2;


import buiduyanh.fpolyhn.asm2.API.ApiService;
import buiduyanh.fpolyhn.asm2.SharedPreferences.MySharedPreferences;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String BASE_URL = MySharedPreferences.URL+"/api/";
//        private static final String BASE_URL = "http://192.168.1.9:3000/api/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}
