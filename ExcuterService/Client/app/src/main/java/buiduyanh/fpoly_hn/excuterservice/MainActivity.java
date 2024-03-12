package buiduyanh.fpoly_hn.excuterservice;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executorService = Executors.newCachedThreadPool();
//

        try {
            Future<Void> future = executorService.submit(new MyCallable() );
            if(future.get()!=null){

                Log.d("ExecuterService",future.get().toString());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }


    // lấy tất cả dữ liệu về
    private JSONArray callAPIGetData(String urlString) throws IOException, JSONException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            JSONArray jsonArray = new JSONArray(result.toString());

            // Log dữ liệu đã nhận được
            Log.d("GetData", "Data received: " + jsonArray.toString());

            return jsonArray;
        } finally {
            urlConnection.disconnect();
        }
    }


    // Thêm dữ liệu mới
    private void callAPIAddData(String urlString, String name, String price, String brand) throws IOException, JSONException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Tạo đối tượng JSON từ dữ liệu
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("price", price);
            jsonObject.put("brand", brand);

            // Gửi dữ liệu
            DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
            outStream.writeBytes(jsonObject.toString());
            outStream.flush();
            outStream.close();
// Log dữ liệu đã thêm mới
            Log.d("AddData", "Name: " + name + ", Price: " + price + ", Brand: " + brand);
            // Đọc phản hồi từ server (nếu cần)
            InputStream inputStream = connection.getInputStream();
            // Xử lý phản hồi (nếu cần)
        } finally {
            connection.disconnect();
        }
    }

    // Sửa đổi dữ liệu
    private void callAPIUpdateData(String urlString, String id, String name, String price, String brand) throws IOException, JSONException {
        URL url = new URL(urlString + "/" + id); // Đảm bảo rằng URL có chứa ID của mục cần sửa đổi
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("PUT"); // Hoặc POST nếu API của bạn sử dụng POST cho yêu cầu sửa đổi
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Tạo đối tượng JSON từ dữ liệu cần cập nhật
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("price", price);
            jsonObject.put("brand", brand);

            // Gửi dữ liệu
            DataOutputStream outStream = new DataOutputStream(connection.getOutputStream());
            outStream.writeBytes(jsonObject.toString());
            outStream.flush();
            outStream.close();

            Log.d("UpdateData", "ID: " + id + ", Name: " + name + ", Price: " + price + ", Brand: " + brand);

            // Đọc phản hồi từ server (nếu cần)
            InputStream inputStream = connection.getInputStream();
            // Xử lý phản hồi (nếu cần)
        } finally {
            connection.disconnect();
        }
    }


    // xoaas
    private void callAPIDeleteData(String urlString, String id) throws IOException {
        URL url = new URL(urlString + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("DELETE");

            // Đọc phản hồi từ máy chủ (nếu cần)
            InputStream inputStream = connection.getInputStream();
            // Xử lý phản hồi (nếu cần)
        } finally {
            connection.disconnect();
        }
    }



    public class MyCallable implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            // Thêm dữ liệu mới
         //   callAPIAddData("http://192.168.0.101:3000/product/post", "so 3", "3", "3");
            //
            callAPIGetData("http://192.168.0.101:3000/users");

            // xoá
         //     callAPIDeleteData("http://192.168.0.101:3000/delete", "65f09d377d84baea3fe2d2b1");

            // Sửa đổi dữ liệu
         //  String idToUpdate = "65f09d477d84baea3fe2d2b4"; // ID của mục cần sửa đổi
          //  callAPIUpdateData("http://192.168.0.101:3000/product/update", idToUpdate, "Test 3", "30.00", "xe may");

            return null;
        }
    }

}