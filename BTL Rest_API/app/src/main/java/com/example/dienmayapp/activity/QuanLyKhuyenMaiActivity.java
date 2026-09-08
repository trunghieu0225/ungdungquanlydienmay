package com.example.dienmayapp.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.PromotionAPI;
import com.example.dienmayapp.api.PromotionRequest;
import com.example.dienmayapp.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyKhuyenMaiActivity extends AppCompatActivity {

    private EditText edtCode;
    private EditText edtDiscount;
    private EditText edtMinTotal;

    private Button btnAddPromotion;
    private Button btnUpdatePromotion;
    private Button btnDeletePromotion;

    private ListView lvPromotions;

    private ApiService apiService;

    private ArrayList<PromotionAPI> promotionList;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;

    private PromotionAPI selectedPromotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_khuyenmai);

        edtCode = findViewById(R.id.edtCode);
        edtDiscount = findViewById(R.id.edtDiscount);
        edtMinTotal = findViewById(R.id.edtMinTotal);

        btnAddPromotion = findViewById(R.id.btnAddPromotion);
        btnUpdatePromotion = findViewById(R.id.btnUpdatePromotion);
        btnDeletePromotion = findViewById(R.id.btnDeletePromotion);

        lvPromotions = findViewById(R.id.lvPromotions);

        apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);

        promotionList = new ArrayList<>();
        displayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );

        lvPromotions.setAdapter(adapter);

        loadPromotions();

        btnAddPromotion.setOnClickListener(v -> addPromotion());

        btnUpdatePromotion.setOnClickListener(v -> updatePromotion());

        btnDeletePromotion.setOnClickListener(v -> deletePromotion());

        lvPromotions.setOnItemClickListener((parent, view, position, id) -> {

            selectedPromotion = promotionList.get(position);

            edtCode.setText(selectedPromotion.getCode());
            edtDiscount.setText(String.valueOf(selectedPromotion.getDiscount()));
            edtMinTotal.setText(String.valueOf(selectedPromotion.getMinTotal()));

        });

    }

    private void loadPromotions() {

        apiService.getPromotions().enqueue(new Callback<List<PromotionAPI>>() {

            @Override
            public void onResponse(Call<List<PromotionAPI>> call,
                                   Response<List<PromotionAPI>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    promotionList.clear();
                    displayList.clear();

                    promotionList.addAll(response.body());

                    for (PromotionAPI p : promotionList) {

                        displayList.add(
                                p.getCode()
                                        + " | Giảm: "
                                        + p.getDiscount()
                                        + " | Đơn từ: "
                                        + p.getMinTotal()
                        );

                    }

                    adapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(
                            QuanLyKhuyenMaiActivity.this,
                            "Không tải được dữ liệu",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<List<PromotionAPI>> call,
                                  Throwable t) {

                Toast.makeText(
                        QuanLyKhuyenMaiActivity.this,
                        "Không kết nối được server",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

    private void addPromotion() {

        String code = edtCode.getText().toString().trim();
        String discount = edtDiscount.getText().toString().trim();
        String minTotal = edtMinTotal.getText().toString().trim();

        if (code.isEmpty() ||
                discount.isEmpty() ||
                minTotal.isEmpty()) {

            Toast.makeText(
                    this,
                    "Vui lòng nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        PromotionRequest request = new PromotionRequest();

        request.setCode(code);
        request.setDiscount(Double.parseDouble(discount));
        request.setMinTotal(Double.parseDouble(minTotal));

        apiService.addPromotion(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    QuanLyKhuyenMaiActivity.this,
                                    "Thêm voucher thành công",
                                    Toast.LENGTH_SHORT
                            ).show();

                            clearForm();

                            loadPromotions();

                        } else {

                            Toast.makeText(
                                    QuanLyKhuyenMaiActivity.this,
                                    "Thêm voucher thất bại",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call,
                                          Throwable t) {

                        Toast.makeText(
                                QuanLyKhuyenMaiActivity.this,
                                "Không kết nối được server",
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    private void updatePromotion() {

        if (selectedPromotion == null) {

            Toast.makeText(
                    this,
                    "Vui lòng chọn voucher",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        PromotionRequest request = new PromotionRequest();

        request.setCode(
                edtCode.getText().toString().trim()
        );

        request.setDiscount(
                Double.parseDouble(
                        edtDiscount.getText().toString().trim()
                )
        );

        request.setMinTotal(
                Double.parseDouble(
                        edtMinTotal.getText().toString().trim()
                )
        );

        apiService.updatePromotion(
                selectedPromotion.getCode(),
                request
        ).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            QuanLyKhuyenMaiActivity.this,
                            "Cập nhật thành công",
                            Toast.LENGTH_SHORT
                    ).show();

                    clearForm();

                    loadPromotions();

                } else {

                    Toast.makeText(
                            QuanLyKhuyenMaiActivity.this,
                            "Cập nhật thất bại",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call,
                                  Throwable t) {

                Toast.makeText(
                        QuanLyKhuyenMaiActivity.this,
                        "Không kết nối được server",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

    private void deletePromotion() {

        if (selectedPromotion == null) {

            Toast.makeText(
                    this,
                    "Vui lòng chọn voucher",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        apiService.deletePromotion(
                selectedPromotion.getCode()
        ).enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call,
                                   Response<Void> response) {

                if (response.isSuccessful()) {

                    Toast.makeText(
                            QuanLyKhuyenMaiActivity.this,
                            "Xóa voucher thành công",
                            Toast.LENGTH_SHORT
                    ).show();

                    clearForm();

                    loadPromotions();

                } else {

                    Toast.makeText(
                            QuanLyKhuyenMaiActivity.this,
                            "Xóa voucher thất bại",
                            Toast.LENGTH_SHORT
                    ).show();

                }

            }

            @Override
            public void onFailure(Call<Void> call,
                                  Throwable t) {

                Toast.makeText(
                        QuanLyKhuyenMaiActivity.this,
                        "Không kết nối được server",
                        Toast.LENGTH_SHORT
                ).show();

            }

        });

    }

    private void clearForm() {

        selectedPromotion = null;

        edtCode.setText("");
        edtDiscount.setText("");
        edtMinTotal.setText("");

    }

}