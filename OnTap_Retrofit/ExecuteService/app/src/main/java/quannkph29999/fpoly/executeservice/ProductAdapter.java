package quannkph29999.fpoly.executeservice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
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

import java.util.List;

import quannkph29999.fpoly.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    public List<Product> products;


    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public void setDataProduct(List<Product> newData) {
        products.clear();
        products.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_getdata, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.brand.setText(product.getBrand());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText editname = view.findViewById(R.id.edt_editname);
                EditText editprice = view.findViewById(R.id.edt_editprice);
                EditText editbrand = view.findViewById(R.id.edt_editbrand);
                editname.setText(product.getName());
                editprice.setText(String.valueOf(product.getPrice()));
                editbrand.setText(product.getBrand());
                Button btn_sua = view.findViewById(R.id.bt_updateProduct);
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Product newproduct = new Product();
                        String nameedit = editname.getText().toString();
                        int priceedit = Integer.parseInt(editprice.getText().toString());
                        String brandedit = editbrand.getText().toString();
                        newproduct.setName(nameedit);
                        newproduct.setPrice(priceedit);
                        newproduct.setBrand(brandedit);
                        ApiService.apiservice.EditProduct(product.get_id(), newproduct).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                if (response.isSuccessful()) {
                                    products.set(position,response.body());
                                    notifyItemChanged(position);
                                    notifyItemRangeChanged(position,products.size());
                                    Toast.makeText(context, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {
                                alertDialog.dismiss();

                            }
                        });
                    }
                });

                alertDialog.show();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(product.get_id(), position);
            }
        });

    }

    private void showDeleteConfirmationDialog(final String productId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa sản phẩm này không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ApiService.apiservice.DeleteProduct(productId).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Đã xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    removeProduct(position);
                                } else {
                                    Toast.makeText(context, "Đã xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();
    }

    public void removeProduct(int position) {
        products.remove(position);
        notifyItemRemoved(position);
    }




    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, brand;
        ImageView edit, delete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_price);
            brand = itemView.findViewById(R.id.tv_brand);
            edit = itemView.findViewById(R.id.item_editproduct);
            delete = itemView.findViewById(R.id.item_deleteproduct);
        }
    }
}
