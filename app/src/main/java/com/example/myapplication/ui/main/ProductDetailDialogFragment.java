package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.models.CartItem;
import com.example.myapplication.data.models.Product;
import com.example.myapplication.data.repository.CartRepository;

public class ProductDetailDialogFragment extends DialogFragment {
    private Product product;
    private CartRepository cartRepository;

    public ProductDetailDialogFragment(Product product) {
        this.product = product;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);

        cartRepository = new CartRepository(getContext());

        ImageView imageView = v.findViewById(R.id.productDetailImage);
        TextView name = v.findViewById(R.id.productDetailName);
        TextView price = v.findViewById(R.id.productDetailPrice);
        TextView description = v.findViewById(R.id.productDetailDescription);
        TextView details = v.findViewById(R.id.productDetailCategory);
        Button addToCart = v.findViewById(R.id.addToCartDetailButton);
        Button closeBtn = v.findViewById(R.id.closeDetailButton);

        name.setText(product.getName());
        price.setText("₽" + String.format("%.2f", product.getPrice()));
        description.setText(product.getDescription());
        details.setText("Тип: " + product.getType() + "\nМатериал: " + product.getMaterial() +
                "\nПроба: " + product.getProbaInfo() + "\nСтатус: " + product.getStatus());

        Glide.with(requireContext())
                .load(product.getImage_url())
                .placeholder(R.drawable.ic_placeholder)
                .into(imageView);

        addToCart.setOnClickListener(b -> {
            CartItem cartItem = new CartItem(String.valueOf(product.getId()), product.getName(),
                    product.getPrice(), 1, product.getImage_url());
            cartRepository.addToCart(cartItem);
            Toast.makeText(getContext(), product.getName() + " добавлен в корзину!", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        closeBtn.setOnClickListener(b -> dismiss());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
