package com.example.myapplication.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> products;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.onProductClickListener = listener;
    }

    public void updateList(List<Product> newList) {
        products = newList;
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(products.get(position), onProductClickListener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productType;
        ImageView productImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productType = itemView.findViewById(R.id.productDescription);
            productImage = itemView.findViewById(R.id.productImage);
        }

        public void bind(Product product, OnProductClickListener listener) {
            productName.setText(product.getName());
            productPrice.setText("₽" + String.format("%.2f", product.getPrice()));
            // ✅ ИСПРАВЛЕННЫЙ: используем getType() и getMaterial()
            productType.setText(product.getType() + " • " + product.getMaterial());

            // ✅ ИСПРАВЛЕННЫЙ: используем getImage_url()
            Glide.with(itemView.getContext())
                    .load(product.getImage_url())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(productImage);

            itemView.setOnClickListener(v -> listener.onProductClick(product));
        }
    }
}
