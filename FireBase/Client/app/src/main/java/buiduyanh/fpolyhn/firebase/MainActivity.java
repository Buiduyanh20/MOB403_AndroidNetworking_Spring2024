package buiduyanh.fpolyhn.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNhap;
    private Button BtnNhap, BtnGet, BtnDelete;
    private TextView TvHienThi;

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

        edtNhap = findViewById(R.id.edtNhap);
        TvHienThi = findViewById(R.id.TvText);
        BtnNhap = findViewById(R.id.BtnNhap);
        BtnGet = findViewById(R.id.BtnGet);
        BtnDelete = findViewById(R.id.BtnDelete);
        BtnNhap.setOnClickListener(this);
        BtnGet.setOnClickListener(this);
        BtnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.BtnNhap){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("name");
            myRef.setValue(edtNhap.getText().toString());
        }if (view.getId()==R.id.BtnGet){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("name");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String result = snapshot.getValue(String.class);
                    TvHienThi.setText(result);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }if (view.getId()==R.id.BtnDelete) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("name");
            myRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Xóa thành công
                    TvHienThi.setText(""); // Xóa nội dung hiển thị
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Xử lý lỗi nếu cần
                }
            });
        }


        }
}