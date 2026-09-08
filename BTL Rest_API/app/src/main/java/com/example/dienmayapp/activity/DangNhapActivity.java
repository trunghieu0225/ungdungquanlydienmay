package com.example.dienmayapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.RetrofitClient;
import com.example.dienmayapp.api.LoginRequest;
import com.example.dienmayapp.api.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.database.DatabaseManager;

public class DangNhapActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView txtRegister;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        apiService =
                RetrofitClient
                        .getClient()
                        .create(ApiService.class);

        btnLogin.setOnClickListener(v -> {

            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                        DangNhapActivity.this,
                        "Vui lòng nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            LoginRequest request =
                    new LoginRequest(
                            username,
                            password
                    );

            apiService.login(request)
                    .enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(
                                Call<LoginResponse> call,
                                Response<LoginResponse> response) {

                            if (response.isSuccessful()
                                    && response.body() != null) {

                                LoginResponse user =
                                        response.body();

                                SharedPreferences prefs =
                                        getSharedPreferences(
                                                "USER_FILE",
                                                MODE_PRIVATE
                                        );

                                SharedPreferences.Editor editor =
                                        prefs.edit();

                                editor.putBoolean(
                                        "isLogin",
                                        true
                                );

                                editor.putString(
                                        "username",
                                        user.getUsername()
                                );

                                editor.putString(
                                        "fullname",
                                        user.getFullname()
                                );

                                editor.putString(
                                        "role",
                                        user.getRole()
                                );

                                editor.apply();

                                Toast.makeText(
                                        DangNhapActivity.this,
                                        "Đăng nhập thành công",
                                        Toast.LENGTH_SHORT
                                ).show();

                                if (user.getRole().equalsIgnoreCase("ADMIN")) {

                                    startActivity(
                                            new Intent(
                                                    DangNhapActivity.this,
                                                    TrangchuAdmin.class
                                            )
                                    );

                                } else {

                                    startActivity(
                                            new Intent(
                                                    DangNhapActivity.this,
                                                    TrangchuUser.class
                                            )
                                    );

                                }

                                finish();

                            } else {

                                Toast.makeText(
                                        DangNhapActivity.this,
                                        "Sai tài khoản hoặc mật khẩu",
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                        }

                        @Override
                        public void onFailure(
                                Call<LoginResponse> call,
                                Throwable t) {

                            Toast.makeText(
                                    DangNhapActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    });

        });

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
            startActivity(intent);
        });
    }
}