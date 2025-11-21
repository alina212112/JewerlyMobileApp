package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import com.example.myapplication.data.repository.AuthRepository;
import com.example.myapplication.ui.auth.LoginActivity;

public class ProfileFragment extends Fragment {
    private AuthRepository authRepository;
    private TextView userEmailText, userNameText, userPhoneText, userLoginText;
    private Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        authRepository = new AuthRepository(getContext());

        userEmailText = view.findViewById(R.id.userEmailText);
        userNameText = view.findViewById(R.id.userNameText);
        userPhoneText = view.findViewById(R.id.userPhoneText);
        userLoginText = view.findViewById(R.id.userLoginText);
        logoutButton = view.findViewById(R.id.logoutButton);

        displayUserInfo();

        logoutButton.setOnClickListener(v -> {
            authRepository.logout();
            Toast.makeText(getContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), LoginActivity.class));
            requireActivity().finish();
        });

        return view;
    }

    private void displayUserInfo() {
        String email = authRepository.getCurrentEmail();
        String fullName = authRepository.getUserFullName();
        String userId = authRepository.getCurrentUserId();

        userEmailText.setText("Email: " + (email != null ? email : "Не указан"));
        userNameText.setText("Имя: " + (fullName != null ? fullName : "Не указано"));
        userPhoneText.setText("Телефон: +7 (999) 123-45-67");
        userLoginText.setText("Логин: user_" + (userId != null ? userId : "123"));
    }

    @Override
    public void onResume() {
        super.onResume();
        displayUserInfo();
    }
}
