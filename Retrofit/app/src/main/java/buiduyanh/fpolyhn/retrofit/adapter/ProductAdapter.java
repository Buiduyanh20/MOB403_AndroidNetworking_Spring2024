package buiduyanh.fpolyhn.retrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import buiduyanh.fpolyhn.retrofit.R;
import buiduyanh.fpolyhn.retrofit.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private Context mContext;
    private ArrayList<Product> products;

    public ProductAdapter(Context mContext, ArrayList<Product> products) {
        this.mContext = mContext;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_product,parent,false);
        return new ProductHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product mProduct = products.get(position);
        holder.tvName.setText(mProduct.getName());
        holder.tvPrice.setText(mProduct.getPrice());
        holder.tvBrand.setText(mProduct.getBrand());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        }
        return 0;
    }

    public static class ProductHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvPrice, tvBrand ;
        private ImageView imgDelete,imgEdit;
        public ProductHolder(View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvBrand = itemView.findViewById(R.id.tvBrand);

            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
}
