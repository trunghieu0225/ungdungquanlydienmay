package com.example.dienmayapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.RetrofitClient;
import com.example.dienmayapp.api.UserRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {

    EditText edtFullName, edtRegUsername, edtRegPassword, edtConfirmPassword;
    Button btnRegister;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        edtFullName = findViewById(R.id.edtFullName);
        edtRegUsername = findViewById(R.id.edtRegUsername);
        edtRegPassword = findViewById(R.id.edtRegPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);

        btnRegister.setOnClickListener(v -> {

            String username = edtRegUsername.getText().toString().trim();
            String password = edtRegPassword.getText().toString().trim();
            String fullname = edtFullName.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (username.isEmpty()
                    || password.isEmpty()
                    || fullname.isEmpty()) {

                Toast.makeText(
                        DangKyActivity.this,
                        "Nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            UserRequest request = new UserRequest(
                    username,
                    password,
                    fullname,
                    "USER"
            );

            apiService.register(request)
                    .enqueue(new Callback<Void>() {

                        @Override
                        public void onResponse(Call<Void> call,
                                               Response<Void> response) {

                            if(response.isSuccessful()){

                                Toast.makeText(
                                        DangKyActivity.this,
                                        "Đăng ký thành công",
                                        Toast.LENGTH_SHORT
                                ).show();

                                finish();

                            }else{

                                Toast.makeText(
                                        DangKyActivity.this,
                                        "Đăng ký thất bại",
                                        Toast.LENGTH_SHORT
                                ).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call,
                                              Throwable t) {

                            Toast.makeText(
                                    DangKyActivity.this,
                                    t.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();

                        }

                    });

        });
    }
}