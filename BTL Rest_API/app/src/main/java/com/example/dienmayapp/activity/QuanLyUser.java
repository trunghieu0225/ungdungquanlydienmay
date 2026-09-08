package com.example.dienmayapp.activity;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.btl.R;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.RetrofitClient;
import com.example.dienmayapp.api.UserAPI;
import com.example.dienmayapp.api.UserRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class QuanLyUser extends AppCompatActivity {

    EditText edtUserId, edtUserName, edtUserPassword, edtUserFullName, edtUserRole, edtSearchUser;
    Button btnAddUser, btnUpdateUser, btnDeleteUser, btnSearchUser;
    ListView lvUsers;

    ApiService apiService;
    ArrayAdapter<String> userAdapter;
    ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_nhanvien);

        apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);

        edtUserId = findViewById(R.id.edtUserId);
        edtUserName = findViewById(R.id.edtUserName);
        edtUserPassword = findViewById(R.id.edtUserPassword);
        edtUserFullName = findViewById(R.id.edtUserFullName);
        edtUserRole = findViewById(R.id.edtUserRole);
        edtSearchUser = findViewById(R.id.edtSearchUser);

        btnAddUser = findViewById(R.id.btnAddUser);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        btnSearchUser = findViewById(R.id.btnSearchUser);
        lvUsers = findViewById(R.id.lvUsers);

        userList = new ArrayList<>();

        userAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                userList
        );

        lvUsers.setAdapter(userAdapter);

        loadUsers();

        btnAddUser.setOnClickListener(v -> addUser());
        btnUpdateUser.setOnClickListener(v -> updateUser());
        btnDeleteUser.setOnClickListener(v -> deleteUser());
        btnSearchUser.setOnClickListener(v -> searchUser());

        lvUsers.setOnItemClickListener((parent, view, position, id) -> {
            String item = userList.get(position);
            String[] parts = item.split("\\|");
            if (parts.length >= 5) {
                edtUserId.setText(parts[0].trim());
                edtUserName.setText(parts[1].trim());
                edtUserPassword.setText(parts[2].trim());
                edtUserFullName.setText(parts[3].trim());
                edtUserRole.setText(parts[4].trim());
            }
        });
    }

    private void loadUsers() {

        apiService.getUsers().enqueue(new Callback<List<UserAPI>>() {

            @Override
            public void onResponse(Call<List<UserAPI>> call,
                                   Response<List<UserAPI>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    userList.clear();

                    for (UserAPI u : response.body()) {

                        userList.add(
                                u.getUserId() + " | "
                                        + u.getUsername() + " | "
                                        + u.getPassword() + " | "
                                        + u.getFullname() + " | "
                                        + u.getRole()
                        );
                    }

                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<UserAPI>> call,
                                  Throwable t) {

                Toast.makeText(
                        QuanLyUser.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });

    }

    private void addUser() {

        String username = edtUserName.getText().toString().trim();
        String password = edtUserPassword.getText().toString().trim();
        String fullname = edtUserFullName.getText().toString().trim();
        String role = edtUserRole.getText().toString().trim();

        if (username.isEmpty()
                || password.isEmpty()
                || fullname.isEmpty()
                || role.isEmpty()) {

            Toast.makeText(
                    this,
                    "Nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        UserRequest request =
                new UserRequest(
                        username,
                        password,
                        fullname,
                        role
                );

        apiService.addUser(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    QuanLyUser.this,
                                    "Thêm user thành công",
                                    Toast.LENGTH_SHORT
                            ).show();

                            clearFields();

                            loadUsers();

                        }else{

                            Toast.makeText(
                                    QuanLyUser.this,
                                    "Không thể thêm user",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyUser.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void updateUser() {

        String idStr = edtUserId.getText().toString().trim();

        if(idStr.isEmpty()){

            Toast.makeText(
                    this,
                    "Chọn user cần sửa",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        int id = Integer.parseInt(idStr);

        String username = edtUserName.getText().toString().trim();
        String password = edtUserPassword.getText().toString().trim();
        String fullname = edtUserFullName.getText().toString().trim();
        String role = edtUserRole.getText().toString().trim();

        UserRequest request =
                new UserRequest(
                        username,
                        password,
                        fullname,
                        role
                );

        apiService.updateUser(id, request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    QuanLyUser.this,
                                    "Cập nhật thành công",
                                    Toast.LENGTH_SHORT
                            ).show();

                            clearFields();

                            loadUsers();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyUser.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void deleteUser() {

        String idStr =
                edtUserId.getText().toString().trim();

        if(idStr.isEmpty()){

            Toast.makeText(
                    this,
                    "Chọn user cần xóa",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        int id =
                Integer.parseInt(idStr);

        apiService.deleteUser(id)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    QuanLyUser.this,
                                    "Xóa thành công",
                                    Toast.LENGTH_SHORT
                            ).show();

                            clearFields();

                            loadUsers();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyUser.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void searchUser() {

        String keyword =
                edtSearchUser.getText().toString().trim();

        if(keyword.isEmpty()){

            loadUsers();

            return;

        }

        apiService.searchUser(keyword)
                .enqueue(new Callback<List<UserAPI>>() {

                    @Override
                    public void onResponse(
                            Call<List<UserAPI>> call,
                            Response<List<UserAPI>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            userList.clear();

                            for(UserAPI u : response.body()){

                                userList.add(

                                        u.getUserId()
                                                + " | "
                                                + u.getUsername()
                                                + " | "
                                                + u.getPassword()
                                                + " | "
                                                + u.getFullname()
                                                + " | "
                                                + u.getRole()

                                );

                            }

                            userAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<UserAPI>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyUser.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void clearFields() {
        edtUserId.setText("");
        edtUserName.setText("");
        edtUserPassword.setText("");
        edtUserFullName.setText("");
        edtUserRole.setText("");
        edtSearchUser.setText("");
    }
}
