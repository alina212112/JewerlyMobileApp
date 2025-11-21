package com.example.myapplication.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnQuantityChangeListener onQuantityChangeListener;

    public interface OnQuantityChangeListener {
        void onQuantityChange(String productId, int quantity);
    }

    public CartAdapter(List<CartItem> cartItems, OnQuantityChangeListener listener) {
        this.cartItems = cartItems;
        this.onQuantityChangeListener = listener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.bind(cartItems.get(position), onQuantityChangeListener);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        TextView cartName, cartPrice, quantityText;
        Button minusBtn, plusBtn, deleteBtn;

        public CartViewHolder(View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartName = itemView.findViewById(R.id.cartName);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            quantityText = itemView.findViewById(R.id.quantityText);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }

        public void bind(CartItem item, OnQuantityChangeListener listener) {
            cartName.setText(item.getProductName());
            cartPrice.setText("₽" + String.format("%.2f", item.getPrice() * item.getQuantity()));
            quantityText.setText(String.valueOf(item.getQuantity()));

            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(cartImage);

            minusBtn.setOnClickListener(v -> listener.onQuantityChange(item.getProductId(), item.getQuantity() - 1));
            plusBtn.setOnClickListener(v -> listener.onQuantityChange(item.getProductId(), item.getQuantity() + 1));
            deleteBtn.setOnClickListener(v -> listener.onQuantityChange(item.getProductId(), 0));
        }
    }
}
