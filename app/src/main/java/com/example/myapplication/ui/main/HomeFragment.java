package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.models.Product;
import com.example.myapplication.data.repository.ProductRepository;
import java.util.List;

public class HomeFragment extends Fragment implements ProductRepository.ProductCallback {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private SearchView searchView;
    private ProductRepository productRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.productsRecyclerView);
        searchView = view.findViewById(R.id.searchView);

        productRepository = new ProductRepository();
        productRepository.setProductCallback(this);

        setupRecycler(new java.util.ArrayList<>());
        setupSearch();
        productRepository.getAllProducts();

        return view;
    }

    private void setupRecycler(List<Product> products) {
        productAdapter = new ProductAdapter(products, product -> showProductDetails(product));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(productAdapter);
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                List<Product> results = newText.isEmpty() ?
                        productRepository.getAllProductsSync() :
                        productRepository.searchProducts(newText);
                productAdapter.updateList(results);
                return true;
            }
        });
    }

    private void showProductDetails(Product product) {
        ProductDetailDialogFragment dialog = new ProductDetailDialogFragment(product);
        dialog.show(getChildFragmentManager(), "ProductDetail");
    }






    @Override
    public void onProductsLoaded(List<Product> products) {
        if (productAdapter != null) {
            productAdapter.updateList(products);
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), "Ошибка: " + error, Toast.LENGTH_SHORT).show();
    }
}
