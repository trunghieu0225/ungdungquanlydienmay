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
import com.example.dienmayapp.api.CategoryAPI;
import com.example.dienmayapp.api.CategoryRequest;
import com.example.dienmayapp.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class QuanLyLoai extends AppCompatActivity {

    EditText edtCategoryName, edtCategoryDesc, edtSearchCategory;
    Button btnAddCategory, btnUpdateCategory, btnDeleteCategory, btnSearchCategory;
    ListView lvCategories;

    ApiService apiService;
    ArrayAdapter<String> categoryAdapter;
    ArrayList<String> categoryList;

    int selectedCategoryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_loai);

        edtCategoryName = findViewById(R.id.edtCategoryName);
        edtCategoryDesc = findViewById(R.id.edtCategoryDesc);
        edtSearchCategory = findViewById(R.id.edtSearchCategory);

        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnUpdateCategory = findViewById(R.id.btnUpdateCategory);
        btnDeleteCategory = findViewById(R.id.btnDeleteCategory);
        btnSearchCategory = findViewById(R.id.btnSearchCategory);

        lvCategories = findViewById(R.id.lvCategories);

        apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);

        categoryList = new ArrayList<>();

        categoryAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        categoryList
                );

        lvCategories.setAdapter(categoryAdapter);

        loadCategories();

        btnAddCategory.setOnClickListener(v -> addCategory());
        btnUpdateCategory.setOnClickListener(v -> updateCategory());
        btnDeleteCategory.setOnClickListener(v -> deleteCategory());
        btnSearchCategory.setOnClickListener(v -> searchCategory());

        lvCategories.setOnItemClickListener((parent, view, position, id) -> {
            String item = categoryList.get(position);
            String[] parts = item.split("\\|");
            if (parts.length >= 3) {
                selectedCategoryId = Integer.parseInt(parts[0].trim());
                edtCategoryName.setText(parts[1].trim());
                edtCategoryDesc.setText(parts[2].trim());
                Toast.makeText(this, "Đã chọn loại hàng hóa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCategories() {

        apiService.getCategories()
                .enqueue(new Callback<List<CategoryAPI>>() {

                    @Override
                    public void onResponse(
                            Call<List<CategoryAPI>> call,
                            Response<List<CategoryAPI>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            categoryList.clear();

                            for(CategoryAPI c : response.body()){

                                categoryList.add(
                                        c.getCategoryId()
                                                + " | "
                                                + c.getCategoryName()
                                                + " | "
                                                + c.getDescription()
                                );
                            }

                            categoryAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<CategoryAPI>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyLoai.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                });

    }

    private void addCategory() {

        String name =
                edtCategoryName.getText().toString().trim();

        String desc =
                edtCategoryDesc.getText().toString().trim();

        if(name.isEmpty()){

            Toast.makeText(
                    this,
                    "Nhập tên loại",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        CategoryRequest request =
                new CategoryRequest(
                        name,
                        desc
                );

        apiService.addCategory(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        Toast.makeText(
                                QuanLyLoai.this,
                                "Thêm thành công",
                                Toast.LENGTH_SHORT
                        ).show();

                        clearFields();

                        loadCategories();

                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyLoai.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                });

    }

    private void updateCategory() {

        if (selectedCategoryId == -1) {
            Toast.makeText(this,
                    "Hãy chọn loại hàng hóa",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String name = edtCategoryName.getText().toString().trim();
        String desc = edtCategoryDesc.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this,
                    "Nhập tên loại",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        CategoryRequest request =
                new CategoryRequest(name, desc);

        apiService.updateCategory(
                selectedCategoryId,
                request
        ).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(
                    Call<Void> call,
                    Response<Void> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            QuanLyLoai.this,
                            "Cập nhật thành công",
                            Toast.LENGTH_SHORT
                    ).show();

                    clearFields();

                    loadCategories();

                } else {

                    Toast.makeText(
                            QuanLyLoai.this,
                            "Cập nhật thất bại",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }

            @Override
            public void onFailure(
                    Call<Void> call,
                    Throwable t) {

                Toast.makeText(
                        QuanLyLoai.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();

            }
        });

    }

    private void deleteCategory() {

        if (selectedCategoryId == -1) {

            Toast.makeText(
                    this,
                    "Hãy chọn loại hàng",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        apiService.deleteCategory(selectedCategoryId)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if(response.isSuccessful()){

                            Toast.makeText(
                                    QuanLyLoai.this,
                                    "Xóa thành công",
                                    Toast.LENGTH_SHORT
                            ).show();

                            clearFields();

                            loadCategories();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyLoai.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void searchCategory() {

        String keyword =
                edtSearchCategory
                        .getText()
                        .toString()
                        .trim();

        if(keyword.isEmpty()){

            loadCategories();

            return;
        }

        apiService.searchCategory(keyword)
                .enqueue(new Callback<List<CategoryAPI>>() {

                    @Override
                    public void onResponse(
                            Call<List<CategoryAPI>> call,
                            Response<List<CategoryAPI>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            categoryList.clear();

                            for(CategoryAPI c : response.body()){

                                categoryList.add(
                                        c.getCategoryId()
                                                + " | "
                                                + c.getCategoryName()
                                                + " | "
                                                + c.getDescription()
                                );

                            }

                            categoryAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<CategoryAPI>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLyLoai.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void clearFields() {
        selectedCategoryId = -1;
        edtCategoryName.setText("");
        edtCategoryDesc.setText("");
        edtSearchCategory.setText("");
    }
}
