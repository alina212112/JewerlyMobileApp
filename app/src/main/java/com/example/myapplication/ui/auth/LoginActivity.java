package com.example.myapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.models.User;
import com.example.myapplication.data.repository.AuthRepository;

public class LoginActivity extends AppCompatActivity implements AuthRepository.AuthCallback {
    private AuthRepository authRepository;
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authRepository = new AuthRepository(this);
        authRepository.setAuthCallback(this);

        if (authRepository.isUserLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty()) {
            emailInput.setError("Email не может быть пустым");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Некорректный формат email");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordInput.setError("Пароль должен быть не менее 6 символов");
            return;
        }

        loginButton.setEnabled(false);
        loginButton.setText("Загрузка...");
        authRepository.login(email, password);
    }

    @Override
    public void onSuccess(User user) {
        Toast.makeText(this, "Вход выполнен!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onError(String error) {
        loginButton.setEnabled(true);
        loginButton.setText("Вход");
        Toast.makeText(this, "Ошибка: " + error, Toast.LENGTH_SHORT).show();
    }
}
