package com.example.myapplication.data.repository;

import android.content.Context;
import com.example.myapplication.data.models.CartItem;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private static List<CartItem> cartItems = new ArrayList<>();

    public CartRepository(Context context) {
    }

    public void addToCart(CartItem item) {
        for (CartItem existing : cartItems) {
            if (existing.getProductId().equals(item.getProductId())) {
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public void removeFromCart(String productId) {
        cartItems.removeIf(item -> item.getProductId().equals(productId));
    }

    public List<CartItem> getCart() {
        return new ArrayList<>(cartItems);
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void updateQuantity(String productId, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                break;
            }
        }
    }
}
