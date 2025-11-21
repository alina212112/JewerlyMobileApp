package com.example.myapplication.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.models.CartItem;
import com.example.myapplication.data.repository.CartRepository;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView emptyCartText, totalPriceText;
    private CartAdapter cartAdapter;
    private CartRepository cartRepository;
    private Button checkoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.cartRecyclerView);
        emptyCartText = view.findViewById(R.id.emptyCartText);
        totalPriceText = view.findViewById(R.id.totalPriceText);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        cartRepository = new CartRepository(getContext());
        setupRecycler();

        checkoutButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Заказ оформлен!", Toast.LENGTH_SHORT).show();
            cartRepository.clearCart();
            setupRecycler();
        });

        return view;
    }

    private void setupRecycler() {
        List<CartItem> cartItems = cartRepository.getCart();

        if (cartItems.isEmpty()) {
            emptyCartText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            totalPriceText.setText("Итого: ₽0.00");
            checkoutButton.setEnabled(false);
        } else {
            emptyCartText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            checkoutButton.setEnabled(true);

            cartAdapter = new CartAdapter(cartItems, (productId, quantity) -> {
                if (quantity <= 0) {
                    cartRepository.removeFromCart(productId);
                } else {
                    cartRepository.updateQuantity(productId, quantity);
                }
                setupRecycler();
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(cartAdapter);

            double total = cartRepository.getTotalPrice();
            totalPriceText.setText("Итого: ₽" + String.format("%.2f", total));
        }
    }





    @Override
    public void onResume() {
        super.onResume();
        setupRecycler();
    }
}
