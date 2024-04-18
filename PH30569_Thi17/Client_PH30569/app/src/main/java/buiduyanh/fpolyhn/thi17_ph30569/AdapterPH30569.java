package buiduyanh.fpolyhn.thi17_ph30569;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPH30569  extends RecyclerView.Adapter<AdapterPH30569.ViewHolder> {

    private EditText edtMaNV, edtName,edtEmail, edtAddress, edtImage, edtAge;
    private Context context;
    private ArrayList<NhanVienPH30569> list;
    private InterfacePH30569 productInterface;



    public AdapterPH30569(Context context, ArrayList<NhanVienPH30569> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NhanVienPH30569 product = list.get(position);
        String image = product.getImage().toString();
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.baseline_create_24)
                .into(holder.imgAvatar);

        holder.tvMaNV.setText(product.getManv());
        holder.tvName.setText(product.getName());
        holder.tvEmail.setText(product.getEmail());
        holder.tvAddress.setText(product.getAddress());
        holder.tvAge.setText(product.getAge());



        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(product, position);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(product, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaNV,tvName, tvEmail, tvAddress, tvAge;
        ImageView imgAvatar, imgDelete, imgEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaNV = itemView.findViewById(R.id.tvMaNv);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvAge = itemView.findViewById(R.id.tvAge);

            imgAvatar = itemView.findViewById(R.id.ivImage);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }

    private void showEditDialog(NhanVienPH30569 nhanvien, int position) {
        String productId = (list.get(position).get_id());
        Log.d("TAG", "showEditDialog:  "+productId);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        edtMaNV = dialogView.findViewById(R.id.edEditMaNV);
        edtName = dialogView.findViewById(R.id.edEditName);
        edtEmail = dialogView.findViewById(R.id.edEditEmail);
        edtAddress = dialogView.findViewById(R.id.edEditAddress);
        edtImage = dialogView.findViewById(R.id.edEditImage);
        edtAge = dialogView.findViewById(R.id.edEditAge);

        Button btnSave = dialogView.findViewById(R.id.btnSave);

        edtMaNV.setText(nhanvien.getManv());
        edtName.setText(nhanvien.getName());
        edtEmail.setText(nhanvien.getEmail());
        edtAddress.setText(nhanvien.getAddress());
        edtImage.setText(nhanvien.getImage());
        edtAge.setText(nhanvien.getAge());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maNV = edtMaNV.getText().toString();
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String address = edtAddress.getText().toString();
                String image = edtImage.getText().toString();
                String ageStr = edtAge.getText().toString();

                if (maNV.isEmpty() || name.isEmpty() || email.isEmpty() || address.isEmpty() || image.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int age = Integer.parseInt(ageStr);
                        if (age < 18 || age > 65) {
                            Toast.makeText(context, "Tuổi phải từ 18 đến 65", Toast.LENGTH_SHORT).show();
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(context, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                        } else {
                            NhanVienPH30569 updatedProduct = new NhanVienPH30569(productId, maNV, name, email, address, image,ageStr);
                            updateProduct(position, updatedProduct);
                            alertDialog.dismiss();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Tuổi phải là một số", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void showDeleteDialog(NhanVienPH30569 nhanVienPH30151, int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle("Xác nhận xoá");
        dialogBuilder.setMessage("Bạn có chắc chắn muốn xoá nhân viên này không ?");
        dialogBuilder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct(position);
            }
        });
        dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.show();
    }

    private void updateProduct(int position, NhanVienPH30569 nhanVienPH30151) {
        String id = (list.get(position).get_id());

        InterfacePH30569.apiservice.updateProduct(id, nhanVienPH30151).enqueue(new Callback<NhanVienPH30569>() {
            @Override
            public void onResponse(Call<NhanVienPH30569> call, Response<NhanVienPH30569> response) {
                if (response.isSuccessful()) {
                    list.set(position, nhanVienPH30151);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Nhân viên đã được cập nhật thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi khi cập nhật thông tin nhân viên", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NhanVienPH30569> call, Throwable t) {

            }
        });


    }

    private void deleteProduct(int position) {
        String productId = (list.get(position).get_id());
        Log.d("zzzzz", "deleteProduct: "+ productId);

        InterfacePH30569.apiservice.deleteProduct(productId).enqueue(new Callback<NhanVienPH30569>() {
            @Override
            public void onResponse(Call<NhanVienPH30569> call, Response<NhanVienPH30569> response) {
                if (response.isSuccessful()) {
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Nhân Viên đã được xoá", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi khi xoá nhân viên", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NhanVienPH30569> call, Throwable t) {

            }
        });
    }

}
