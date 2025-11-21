package com.example.myapplication.data.repository;

import android.os.Handler;
import android.os.Looper;
import com.example.myapplication.data.models.Product;
import com.example.myapplication.data.remote.SupabaseAPI;
import com.example.myapplication.data.remote.SupabaseClient;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    private final SupabaseAPI supabaseAPI;
    private List<Product> cachedProducts = new ArrayList<>();
    private ProductCallback productCallback;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface ProductCallback {
        void onProductsLoaded(List<Product> products);
        void onError(String error);
    }

    public ProductRepository() {
        this.supabaseAPI = SupabaseClient.getClient().create(SupabaseAPI.class);
    }

    public void setProductCallback(ProductCallback callback) {
        this.productCallback = callback;
    }

    public void getAllProducts() {
        supabaseAPI.getAllProducts("*").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cachedProducts = response.body();
                    if (productCallback != null) productCallback.onProductsLoaded(cachedProducts);
                } else {
                    if (productCallback != null) productCallback.onError("Ошибка загрузки: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                if (productCallback != null) productCallback.onError("Ошибка сети: " + t.getMessage());
            }
        });
    }


    public List<Product> getAllProductsSync() {
        return new ArrayList<>(cachedProducts);
    }

    public List<Product> searchProducts(String query) {
        List<Product> filtered = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Product product : cachedProducts) {
            if (product.getName().toLowerCase().contains(lowerQuery) ||
                    product.getDescription().toLowerCase().contains(lowerQuery) ||
                    product.getType().toLowerCase().contains(lowerQuery) ||
                    product.getMaterial().toLowerCase().contains(lowerQuery)) {
                filtered.add(product);
            }
        }
        return filtered;
    }

    public Product getProductById(int id) {
        for (Product product : cachedProducts) {
            if (product.getId() == id) return product;
        }
        return null;
    }

    public List<Product> getProductsByType(String type) {
        List<Product> filtered = new ArrayList<>();
        for (Product product : cachedProducts) {
            if (product.getType().equalsIgnoreCase(type)) filtered.add(product);
        }
        return filtered;
    }

    public List<Product> getAvailableProducts() {
        List<Product> available = new ArrayList<>();
        for (Product product : cachedProducts) {
            if (product.isAvailable()) available.add(product);
        }
        return available;
    }
}
