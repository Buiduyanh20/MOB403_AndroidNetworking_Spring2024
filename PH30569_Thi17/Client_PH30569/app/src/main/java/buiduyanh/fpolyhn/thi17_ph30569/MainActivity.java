package buiduyanh.fpolyhn.thi17_ph30569;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText edtMaNV, edtName,edtEmail, edtAddress, edtImage, edtAge;
    private FloatingActionButton btnShowAdd;

    RecyclerView recyclerView;
    private AdapterPH30569 adapter;
    private ArrayList<NhanVienPH30569> arrayList =  new ArrayList<>();

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

        recyclerView = findViewById(R.id.recycleView);
        btnShowAdd = findViewById(R.id.btnShowAdd);
        adapter = new AdapterPH30569(MainActivity.this, arrayList);
        getItem();
        btnShowAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View dialogView = inflater.inflate(R.layout.dialog_add, null);
                dialogBuilder.setView(dialogView);

                edtMaNV = dialogView.findViewById(R.id.edMaNV);
                edtName = dialogView.findViewById(R.id.edName);
                edtEmail = dialogView.findViewById(R.id.edEmail);
                edtAddress = dialogView.findViewById(R.id.edAddress);
                edtImage = dialogView.findViewById(R.id.edImage);
                edtAge = dialogView.findViewById(R.id.edAge);
                Button btnadd = dialogView.findViewById(R.id.btnPost);

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                btnadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String maNv = edtMaNV.getText().toString();
                        String name = edtName.getText().toString();
                        String email = edtEmail.getText().toString();
                        String address = edtAddress.getText().toString();
                        String image = edtImage.getText().toString();
                        String ageStr = edtAge.getText().toString();

                        if (maNv.isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty() || image.isEmpty() || ageStr.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                int age = Integer.parseInt(ageStr);
                                if (age < 18 || age > 65) {
                                    Toast.makeText(MainActivity.this, "Tuổi phải từ 18 đến 65", Toast.LENGTH_SHORT).show();
                                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    Toast.makeText(MainActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                                } else {
                                    NhanVienPH30569 nhanvien = new NhanVienPH30569(maNv, name, email, address, image, ageStr);
                                    InterfacePH30569.apiservice.addProduct(nhanvien).enqueue(new Callback<NhanVienPH30569>() {
                                        @Override
                                        public void onResponse(Call<NhanVienPH30569> call, Response<NhanVienPH30569> response) {
                                            Toast.makeText(MainActivity.this, "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();  // Dismiss the dialog here
                                            getItem();
                                        }

                                        @Override
                                        public void onFailure(Call<NhanVienPH30569> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, "Có lỗi xảy ra khi thêm nhân viên", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(MainActivity.this, "Tuổi phải là số", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }


    private void getItem() {
        InterfacePH30569.apiservice.getList().enqueue(new Callback<List<NhanVienPH30569>>() {
            @Override
            public void onResponse(Call<List<NhanVienPH30569>> call, Response<List<NhanVienPH30569>> response) {
                arrayList.clear();
                arrayList.addAll(response.body());
                adapter= new AdapterPH30569(MainActivity.this, arrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                Log.d("zzzzz", "onResponse: " +response.toString());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<NhanVienPH30569>> call, Throwable t) {

            }
        });

    }
}