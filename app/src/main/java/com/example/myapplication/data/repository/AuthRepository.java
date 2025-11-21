package com.example.myapplication.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.myapplication.data.models.User;
import com.example.myapplication.data.remote.SupabaseAPI;
import com.example.myapplication.data.remote.SupabaseClient;
import com.example.myapplication.utils.SharedPreferencesHelper;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private SharedPreferencesHelper prefsHelper;
    private SupabaseAPI api;
    private AuthCallback callback;
    private Handler main = new Handler(Looper.getMainLooper());

    public interface AuthCallback {
        void onSuccess(User user);
        void onError(String error);
    }

    public AuthRepository(Context context) {
        prefsHelper = new SharedPreferencesHelper(context);
        api = SupabaseClient.getClient().create(SupabaseAPI.class);
    }

    public void setAuthCallback(AuthCallback cb) {
        this.callback = cb;
    }

    // ===== LOGIN =====
    public void login(String email, String password) {

        api.getUserByEmail("eq." + email, "*").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                main.post(() -> {
                    if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                        if (callback != null) callback.onError("Пользователь не найден");
                        return;
                    }

                    User u = response.body().get(0);

                    if (!password.equals(u.getPassword())) {
                        if (callback != null) callback.onError("Неверный пароль");
                        return;
                    }

                    prefsHelper.saveUserSession(email, String.valueOf(u.getId()));
                    prefsHelper.saveUserFullName(u.getName());

                    if (callback != null) callback.onSuccess(u);
                });
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                main.post(() -> {
                    if (callback != null) callback.onError("Ошибка сети: " + t.getMessage());
                });
            }
        });
    }

    public void register(String name, String login, String password, String email, String phone) {

        User u = new User(name, login, phone, email, password);
        u.setId(null);
        u.setCreated_at(null);

        api.createUser(u).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                main.post(() -> {
                    if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                        String error = "Ошибка регистрации (" + response.code() + ")";
                        try {
                            if (response.errorBody() != null) error += ": " + response.errorBody().string();
                        } catch (Exception ignored) {}
                        if (callback != null) callback.onError(error);
                        return;
                    }

                    User created = response.body().get(0);

                    prefsHelper.saveUserSession(email, String.valueOf(created.getId()));
                    prefsHelper.saveUserFullName(created.getName());

                    if (callback != null) callback.onSuccess(created);
                });
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                main.post(() -> {
                    if (callback != null) callback.onError("Сбой сети: " + t.getMessage());
                });
            }
        });
    }

    // ===== UTILS =====
    public void logout() {
        prefsHelper.clearUserSession();
    }

    public boolean isUserLoggedIn() {
        return prefsHelper.getUserSession() != null;
    }

    public String getCurrentUserId() {
        return prefsHelper.getUserId();
    }

    public String getCurrentEmail() {
        return prefsHelper.getUserSession();
    }

    public String getUserFullName() {
        return prefsHelper.getUserFullName();
    }
}
