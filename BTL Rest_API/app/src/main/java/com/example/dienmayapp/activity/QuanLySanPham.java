package com.example.dienmayapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dienmayapp.api.CategoryAPI;
import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.ProductApiRequest;
import com.example.dienmayapp.api.RetrofitClient;
import com.example.dienmayapp.api.ProductAPI;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;


import java.util.ArrayList;

public class QuanLySanPham extends AppCompatActivity {

    EditText edtTen, edtGia, edtGiaCu, edtQua, edtDanhGia, edtMoTa, edtSearchProduct, edtAnh;
    Spinner spLoai;
    Button btnChooseImage, btnAddProduct, btnUpdateProduct, btnDeleteProduct, btnSearchProduct;
    ImageView imgPreview;
    TextView txtImagePath;
    ListView lvProducts;

    ApiService apiService;


    ArrayAdapter<String> productAdapter;
    ArrayAdapter<String> loaiAdapter;
    ArrayList<String> productList;
    ArrayList<String> categoryNames;
    ArrayList<String> danhSachLoaiHienThi;

    String selectedImageUri = "";
    int selectedProductId = -1;

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri.toString();
                    edtAnh.setText("");
                    imgPreview.setImageURI(uri);
                    txtImagePath.setText("Đã chọn ảnh từ máy");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_sanpham);

        edtTen = findViewById(R.id.edtTen);
        edtGia = findViewById(R.id.edtGia);
        edtGiaCu = findViewById(R.id.edtGiaCu);
        edtQua = findViewById(R.id.edtQua);
        edtDanhGia = findViewById(R.id.edtDanhGia);
        spLoai = findViewById(R.id.spLoai);
        edtMoTa = findViewById(R.id.edtMoTa);
        edtAnh = findViewById(R.id.edtAnh);
        edtSearchProduct = findViewById(R.id.edtSearchProduct);

        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        btnSearchProduct = findViewById(R.id.btnSearchProduct);

        imgPreview = findViewById(R.id.imgPreview);
        txtImagePath = findViewById(R.id.txtImagePath);
        lvProducts = findViewById(R.id.lvProducts);



        apiService =
                RetrofitClient
                        .getClient()
                        .create(ApiService.class);

        loadCategoryDropdown();
        loadProducts();

        btnChooseImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));
        btnAddProduct.setOnClickListener(v -> addProduct());
        btnUpdateProduct.setOnClickListener(v -> updateProduct());
        btnDeleteProduct.setOnClickListener(v -> deleteProduct());
        btnSearchProduct.setOnClickListener(v -> searchProduct());

        lvProducts.setOnItemClickListener((parent, view, position, id) -> {

            String item = productList.get(position);

            String[] parts = item.split("\\|");

            if(parts.length >= 6){

                selectedProductId =
                        Integer.parseInt(
                                parts[0].trim()
                        );

                edtTen.setText(
                        parts[1].trim()
                );

                edtGia.setText(
                        parts[2].trim()
                );

                edtGiaCu.setText(
                        parts[3].trim()
                );

                String imageValue =
                        parts[4].trim();

                edtMoTa.setText(
                        parts[5].trim()
                );

                edtAnh.setText(imageValue);

                selectedImageUri = "";

                if(!imageValue.isEmpty()){

                    int resId =
                            getResources()
                                    .getIdentifier(
                                            imageValue,
                                            "drawable",
                                            getPackageName()
                                    );

                    if(resId != 0){

                        imgPreview
                                .setImageResource(
                                        resId
                                );

                        txtImagePath.setText(
                                "Ảnh drawable: "
                                        + imageValue
                        );

                    }else{

                        imgPreview.setImageResource(
                                android.R.drawable
                                        .ic_menu_gallery
                        );

                        txtImagePath.setText(
                                "Không tìm thấy ảnh"
                        );
                    }

                }else{

                    imgPreview.setImageResource(
                            android.R.drawable
                                    .ic_menu_gallery
                    );

                    txtImagePath.setText(
                            "Chưa chọn ảnh"
                    );
                }

                Toast.makeText(
                        QuanLySanPham.this,
                        "Đã chọn sản phẩm",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategoryDropdown();
        loadProducts();
    }

    private void loadCategoryDropdown() {

        apiService.getCategories()
                .enqueue(new Callback<List<CategoryAPI>>() {

                    @Override
                    public void onResponse(Call<List<CategoryAPI>> call,
                                           Response<List<CategoryAPI>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            categoryNames = new ArrayList<>();

                            for (CategoryAPI category : response.body()) {

                                categoryNames.add(
                                        category.getCategoryName()
                                );

                            }

                            danhSachLoaiHienThi = new ArrayList<>();

                            danhSachLoaiHienThi.addAll(categoryNames);

                            danhSachLoaiHienThi.add("Chọn loại");

                            loaiAdapter = new ArrayAdapter<String>(
                                    QuanLySanPham.this,
                                    android.R.layout.simple_spinner_item,
                                    danhSachLoaiHienThi
                            ) {
                                @Override
                                public int getCount() {
                                    return super.getCount() - 1;
                                }
                            };

                            loaiAdapter.setDropDownViewResource(
                                    android.R.layout.simple_spinner_dropdown_item
                            );

                            spLoai.setAdapter(loaiAdapter);

                            spLoai.setSelection(loaiAdapter.getCount());

                        } else {

                            Toast.makeText(
                                    QuanLySanPham.this,
                                    "Không tải được danh sách loại",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<List<CategoryAPI>> call,
                                          Throwable t) {

                        Toast.makeText(
                                QuanLySanPham.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }
                });

    }
    private void loadProducts() {

        if(productList == null){

            productList =
                    new ArrayList<>();

            productAdapter =
                    new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_list_item_1,
                            productList
                    );

            lvProducts.setAdapter(
                    productAdapter
            );
        }

        apiService.getProducts()
                .enqueue(
                        new Callback<List<ProductAPI>>() {

                            @Override
                            public void onResponse(
                                    Call<List<ProductAPI>> call,
                                    Response<List<ProductAPI>> response) {

                                if(response.isSuccessful()
                                        && response.body() != null){

                                    productList.clear();

                                    for(ProductAPI p
                                            : response.body()) {

                                        productList.add(

                                                p.getProductId()
                                                        + " | "
                                                        + p.getName()
                                                        + " | "
                                                        + p.getPrice()
                                                        + " | "
                                                        + p.getOldPrice()
                                                        + " | "
                                                        + p.getGift()
                                                        + " | "
                                                        + p.getRating()
                                                        + " | "
                                                        + p.getImage()
                                                        + " | "
                                                        + p.getDescription()

                                        );
                                    }

                                    productAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(
                                    Call<List<ProductAPI>> call,
                                    Throwable t) {

                                Toast.makeText(
                                        QuanLySanPham.this,
                                        t.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        });
    }

    private String layGiaTriAnhDeLuu() {
        String anhDrawable = edtAnh.getText().toString().trim();

        if (!selectedImageUri.isEmpty()) {
            return selectedImageUri;
        }

        if (!anhDrawable.isEmpty()) {
            return anhDrawable;
        }

        return "";
    }

    private boolean validateProductInput() {
        String ten = edtTen.getText().toString().trim();
        String gia = edtGia.getText().toString().trim();
        String giaCu = edtGiaCu.getText().toString().trim();
        String qua = edtQua.getText().toString().trim();
        String danhGia = edtDanhGia.getText().toString().trim();
        String loai = spLoai.getSelectedItem() != null
                ? spLoai.getSelectedItem().toString().trim()
                : "";
        String moTa = edtMoTa.getText().toString().trim();
        String anh = layGiaTriAnhDeLuu();

        if (ten.isEmpty()) {
            edtTen.setError("Không được để trống tên sản phẩm");
            edtTen.requestFocus();
            return false;
        }

        if (gia.isEmpty()) {
            edtGia.setError("Không được để trống giá");
            edtGia.requestFocus();
            return false;
        }

        if (giaCu.isEmpty()) {
            edtGiaCu.setError("Không được để trống giá cũ");
            edtGiaCu.requestFocus();
            return false;
        }

        if (qua.isEmpty()) {
            edtQua.setError("Không được để trống quà");
            edtQua.requestFocus();
            return false;
        }

        if (danhGia.isEmpty()) {
            edtDanhGia.setError("Không được để trống đánh giá");
            edtDanhGia.requestFocus();
            return false;
        }

        if (spLoai.getSelectedItemPosition() == loaiAdapter.getCount()) {
            Toast.makeText(this, "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (loai.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (moTa.isEmpty()) {
            edtMoTa.setError("Không được để trống mô tả");
            edtMoTa.requestFocus();
            return false;
        }

        if (anh.isEmpty()) {
            Toast.makeText(this, "Hãy chọn ảnh hoặc nhập tên ảnh drawable", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void addProduct() {

        if (!validateProductInput()) {
            return;
        }

        String ten = edtTen.getText().toString().trim();

        double gia = Double.parseDouble(
                edtGia.getText().toString().trim());

        double giaCu = Double.parseDouble(
                edtGiaCu.getText().toString().trim());

        String anh = layGiaTriAnhDeLuu();

        String moTa = edtMoTa.getText().toString().trim();
        String qua = edtQua.getText().toString().trim();
        String danhGia = edtDanhGia.getText().toString().trim();

        ProductApiRequest product =
                new ProductApiRequest(
                        ten,
                        gia,
                        giaCu,
                        anh,
                        moTa,
                        qua,
                        danhGia
                );

        apiService.addProduct(product)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(
                            Call<Void> call,
                            Response<Void> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    QuanLySanPham.this,
                                    "Thêm thành công",
                                    Toast.LENGTH_SHORT
                            ).show();
                            loadProducts();


                            clearFields();

                        } else {

                            Toast.makeText(
                                    QuanLySanPham.this,
                                    "Lỗi: " + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<Void> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLySanPham.this,
                                t.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void updateProduct() {

        if (selectedProductId == -1) {

            Toast.makeText(
                    this,
                    "Hãy chọn sản phẩm cần sửa",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (!validateProductInput()) {
            return;
        }

        String ten =
                edtTen.getText().toString().trim();

        double gia =
                Double.parseDouble(
                        edtGia.getText().toString().trim());

        double giaCu =
                Double.parseDouble(
                        edtGiaCu.getText().toString().trim());

        String anh =
                layGiaTriAnhDeLuu();

        String moTa =
                edtMoTa.getText().toString().trim();
        String qua = edtQua.getText().toString().trim();
        String danhGia = edtDanhGia.getText().toString().trim();

        ProductApiRequest product =
                new ProductApiRequest(
                        ten,
                        gia,
                        giaCu,
                        anh,
                        moTa,
                        qua,
                        danhGia
                );

        apiService.updateProduct(
                selectedProductId,
                product
        ).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(
                    Call<Void> call,
                    Response<Void> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            QuanLySanPham.this,
                            "Sửa thành công",
                            Toast.LENGTH_SHORT
                    ).show();


                    clearFields();
                    loadProducts();

                } else {

                    Toast.makeText(
                            QuanLySanPham.this,
                            "Lỗi: " + response.code(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<Void> call,
                    Throwable t) {

                Toast.makeText(
                        QuanLySanPham.this,
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void deleteProduct() {

        if (selectedProductId == -1) {

            Toast.makeText(
                    this,
                    "Hãy chọn sản phẩm cần xóa",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        apiService.deleteProduct(
                selectedProductId
        ).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(
                    Call<Void> call,
                    Response<Void> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            QuanLySanPham.this,
                            "Xóa thành công",
                            Toast.LENGTH_SHORT
                    ).show();

                    clearFields();
                    loadProducts();

                } else {

                    Toast.makeText(
                            QuanLySanPham.this,
                            "Lỗi: " + response.code(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<Void> call,
                    Throwable t) {

                Toast.makeText(
                        QuanLySanPham.this,
                        t.toString(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void searchProduct() {

        String keyword =
                edtSearchProduct
                        .getText()
                        .toString()
                        .trim();

        if(keyword.isEmpty()){

            loadProducts();
            return;
        }

        apiService.searchProducts(keyword)
                .enqueue(new Callback<List<ProductAPI>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProductAPI>> call,
                            Response<List<ProductAPI>> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            productList.clear();

                            for(ProductAPI p : response.body()){

                                productList.add(

                                        p.getProductId()
                                                + " | "
                                                + p.getName()
                                                + " | "
                                                + p.getPrice()
                                                + " | "
                                                + p.getOldPrice()
                                                + " | "
                                                + p.getGift()
                                                + " | "
                                                + p.getRating()
                                                + " | "
                                                + p.getImage()
                                                + " | "
                                                + p.getDescription()

                                );
                            }

                            productAdapter.notifyDataSetChanged();

                        }else{

                            Toast.makeText(
                                    QuanLySanPham.this,
                                    "Không tìm thấy sản phẩm",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<ProductAPI>> call,
                            Throwable t) {

                        Toast.makeText(
                                QuanLySanPham.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void clearFields() {
        selectedProductId = -1;
        edtTen.setText("");
        edtGia.setText("");
        edtGiaCu.setText("");
        edtQua.setText("");
        edtDanhGia.setText("");
        edtMoTa.setText("");
        edtAnh.setText("");
        edtSearchProduct.setText("");
        selectedImageUri = "";
        txtImagePath.setText("Chưa chọn ảnh");
        imgPreview.setImageResource(android.R.drawable.ic_menu_gallery);

        if (spLoai.getAdapter() != null) {
            spLoai.setSelection(loaiAdapter.getCount());
        }
    }
}