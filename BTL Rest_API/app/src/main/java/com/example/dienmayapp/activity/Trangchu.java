package com.example.dienmayapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.text.DecimalFormat;
import android.content.SharedPreferences;

import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.ProductAPI;
import com.example.dienmayapp.api.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.dienmayapp.adapter.SanphamAdapter;
import com.example.dienmayapp.adapter.SanphamGridAdapter;
import com.example.dienmayapp.model.Sanpham;

import java.util.ArrayList;

public class Trangchu extends AppCompatActivity {

    RecyclerView rvProducts, rvSanPhamGrid;
    LinearLayout btnMenu, btnDangNhap, btnGioHang, btnKhuVuc;
    TextView txtKhuVuc;
    EditText edtSearch;

    ArrayList<Sanpham> tatCaSanPham;
    ArrayList<Sanpham> dsTop;
    ArrayList<Sanpham> dsGrid;

    SanphamAdapter adapterTop;
    SanphamGridAdapter adapterGrid;


    String loaiDangChon = "Tất cả";
    String tuKhoaTimKiem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        SharedPreferences.Editor editor =
                getSharedPreferences("USER_FILE", MODE_PRIVATE).edit();

        editor.clear();
        editor.apply();
        btnMenu = findViewById(R.id.btnMenu);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnGioHang = findViewById(R.id.btnGioHang);
        btnKhuVuc = findViewById(R.id.btnKhuVuc);
        txtKhuVuc = findViewById(R.id.txtKhuVuc);
        edtSearch = findViewById(R.id.edtSearch);

        rvProducts = findViewById(R.id.rvProducts);
        rvSanPhamGrid = findViewById(R.id.rvSanPhamGrid);

        btnDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(Trangchu.this, DangNhapActivity.class);
            startActivity(intent);
        });

        btnGioHang.setOnClickListener(v -> {
            Intent intent = new Intent(Trangchu.this, DangNhapActivity.class);
            startActivity(intent);
        });

        rvProducts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvProducts.setNestedScrollingEnabled(false);

        rvSanPhamGrid.setLayoutManager(new GridLayoutManager(this, 2));
        rvSanPhamGrid.setNestedScrollingEnabled(false);



        tatCaSanPham = new ArrayList<>();
        dsTop = new ArrayList<>();
        dsGrid = new ArrayList<>();

        adapterTop = new SanphamAdapter(this, dsTop);
        rvProducts.setAdapter(adapterTop);

        adapterGrid = new SanphamGridAdapter(this, dsGrid);
        rvSanPhamGrid.setAdapter(adapterGrid);

        taiDuLieuSanPham();
        setupTimKiem();

        btnMenu.setOnClickListener(v -> hienThiPopupMenu());
        btnKhuVuc.setOnClickListener(v -> hienThiMenuKhuVuc());
    }

    @Override
    protected void onResume() {
        super.onResume();
        taiDuLieuSanPham();
    }

    private void taiDuLieuSanPham() {

        ApiService apiService =
                RetrofitClient.getClient()
                        .create(ApiService.class);

        apiService.getProducts()
                .enqueue(new Callback<List<ProductAPI>>() {

                    @Override
                    public void onResponse(
                            Call<List<ProductAPI>> call,
                            Response<List<ProductAPI>> response) {

                        if (response.isSuccessful()
                                && response.body() != null) {

                            tatCaSanPham.clear();

                            DecimalFormat df = new DecimalFormat("#,###");

                            for (ProductAPI p : response.body()) {

                                String gia =
                                        df.format(p.getPrice())
                                                .replace(",", ".")
                                                + "đ";

                                String giaCu =
                                        df.format(p.getOldPrice())
                                                .replace(",", ".")
                                                + "đ";

                                tatCaSanPham.add(

                                        new Sanpham(

                                                p.getName(),

                                                gia,

                                                giaCu,

                                                p.getGift(),

                                                p.getRating(),

                                                p.getImage(),

                                                "Điện máy",

                                                p.getDescription()

                                        )
                                );
                            }

                            locVaHienThiSanPham();
                        }
                    }

                    @Override
                    public void onFailure(
                            Call<List<ProductAPI>> call,
                            Throwable t) {

                        t.printStackTrace();
                    }
                });
    }

    private void setupTimKiem() {
        edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            boolean isSearchAction = actionId == EditorInfo.IME_ACTION_SEARCH;
            boolean isEnterKey = event != null
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && event.getAction() == KeyEvent.ACTION_DOWN;

            if (isSearchAction || isEnterKey) {
                tuKhoaTimKiem = edtSearch.getText().toString().trim();
                locVaHienThiSanPham();
                edtSearch.clearFocus();
                return true;
            }
            return false;
        });
    }

    private void hienThiPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_loaisanpham, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.mnTatCa) {
                    loaiDangChon = "Tất cả";
                } else if (id == R.id.mnMayLanh) {
                    loaiDangChon = "Máy lạnh";
                } else if (id == R.id.mnTivi) {
                    loaiDangChon = "Tivi";
                } else if (id == R.id.mnBinhGiuNhiet) {
                    loaiDangChon = "Bình giữ nhiệt";
                } else if (id == R.id.mnGiaDung) {
                    loaiDangChon = "Gia dụng";
                }

                locVaHienThiSanPham();
                return true;
            }
        });

        popupMenu.show();
    }



    private void hienThiMenuKhuVuc() {
        PopupMenu popupMenu = new PopupMenu(this, btnKhuVuc);
        popupMenu.getMenuInflater().inflate(R.menu.menu_khuvuc, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                txtKhuVuc.setText(" " + item.getTitle().toString());
                return true;
            }
        });

        popupMenu.show();
    }

    private void locVaHienThiSanPham() {
        dsTop.clear();
        dsGrid.clear();

        ArrayList<Sanpham> dsLoc = new ArrayList<>();

        for (Sanpham sp : tatCaSanPham) {
            boolean dungLoai = loaiDangChon.equals("Tất cả") || sp.getLoai().equalsIgnoreCase(loaiDangChon);

            boolean dungTuKhoa = tuKhoaTimKiem.isEmpty()
                    || sp.getTen().toLowerCase().contains(tuKhoaTimKiem.toLowerCase())
                    || sp.getLoai().toLowerCase().contains(tuKhoaTimKiem.toLowerCase())
                    || sp.getMoTa().toLowerCase().contains(tuKhoaTimKiem.toLowerCase());

            if (dungLoai && dungTuKhoa) {
                dsLoc.add(sp);
            }
        }

        for (int i = 0; i < dsLoc.size() && i < 4; i++) {
            dsTop.add(dsLoc.get(i));
        }

        dsGrid.addAll(dsLoc);

        if (adapterTop != null) {
            adapterTop.notifyDataSetChanged();
        }

        if (adapterGrid != null) {
            adapterGrid.notifyDataSetChanged();
        }
    }
}