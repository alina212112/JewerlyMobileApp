package com.example.myapplication.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.models.User;
import com.example.myapplication.data.repository.AuthRepository;

public class RegisterActivity extends AppCompatActivity implements AuthRepository.AuthCallback {
    private AuthRepository authRepository;
    private EditText nameInput, loginInput, emailInput, phoneInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authRepository = new AuthRepository(this);
        authRepository.setAuthCallback(this);

        nameInput = findViewById(R.id.fullNameInput);
        loginInput = findViewById(R.id.loginInput);
        emailInput = findViewById(R.id.emailInput);
        phoneInput = findViewById(R.id.phoneInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);

        registerButton.setOnClickListener(v -> handleRegister());
        backButton.setOnClickListener(v -> finish());
    }

    private void handleRegister() {
        String name = nameInput.getText().toString().trim();
        String login = loginInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (name.isEmpty()) {
            nameInput.setError("Имя не может быть пустым");
            return;
        }

        if (login.isEmpty()) {
            loginInput.setError("Логин не может быть пустым");
            return;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Некорректный email");
            return;
        }

        if (phone.isEmpty() || phone.length() < 10) {
            phoneInput.setError("Некорректный номер телефона");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordInput.setError("Пароль должен быть не менее 6 символов");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Пароли не совпадают");
            return;
        }

        registerButton.setEnabled(false);
        registerButton.setText("Загрузка...");
        authRepository.register(name, login, password, email, phone);
    }

    @Override
    public void onSuccess(User user) {
        Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(String error) {
        registerButton.setEnabled(true);
        registerButton.setText("Регистрация");
        Toast.makeText(this, "Ошибка: " + error, Toast.LENGTH_SHORT).show();
    }
}
