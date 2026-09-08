package com.example.dienmayapp.activity;
import com.example.dienmayapp.api.PromotionAPI;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
//import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dienmayapp.api.ApiService;
import com.example.dienmayapp.api.OrderRequest;
import com.example.dienmayapp.api.RetrofitClient;
import android.content.SharedPreferences;
//import com.example.dienmayapp.database.DatabaseManager;
//import com.example.dienmayapp.database.DatabaseHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.dienmayapp.adapter.GioHangAdapter;

import com.example.dienmayapp.model.GioHang;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity implements GioHangAdapter.OnCartActionListener {

    private RecyclerView recyclerViewCart;
    private GioHangAdapter adapter;

    private TextView txtPromotionHint;
    private TextView tvSubtotal, tvShipping, tvTotal, tvDiscount;

    private double discountAmount = 0;
    private String appliedPromotionCode = "";
    private boolean isPromotionApplied = false;

    private LinearLayout layoutEmpty, layoutSummary, layoutDiscountRow;
    private View layoutPromotion;

    private ScrollView scrollContent;
    private ProgressBar progressBar;

    private Button btnContinueShopping, btnCheckout, btnApplyPromotion;
    private RadioGroup radioGroupPayment;
    private ApiService apiService;
    private com.example.dienmayapp.api.PromotionAPI currentVoucher;
    private List<GioHang> cartItems;

//    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        databaseManager = new DatabaseManager(this);

        setContentView(R.layout.activity_giohang);


        cartItems = QuanLyGioHang.getInstance().getCartItems();

        anhXa();
        setupRecyclerView();
        setupSuKien();
        loadCartData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartData();
    }

    private void anhXa() {
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        layoutSummary = findViewById(R.id.layoutSummary);
        layoutPromotion = findViewById(R.id.layoutPromotion);
        layoutDiscountRow = findViewById(R.id.layoutDiscountRow);
        scrollContent = findViewById(R.id.scrollContent);
        progressBar = findViewById(R.id.progressBar);

        txtPromotionHint = findViewById(R.id.txtPromotionHint);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        tvDiscount = findViewById(R.id.tvDiscount);

        btnContinueShopping = findViewById(R.id.btnContinueShopping);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnApplyPromotion = findViewById(R.id.btnApplyPromotion);

        radioGroupPayment = findViewById(R.id.radioGroupPayment);
    }

    private void setupRecyclerView() {
        adapter = new GioHangAdapter(this, cartItems, this);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(adapter);
    }

    private void setupSuKien() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        btnContinueShopping.setOnClickListener(v -> finish());
        btnCheckout.setOnClickListener(v -> datHang());
        btnApplyPromotion.setOnClickListener(v -> apDungKhuyenMai());
    }

    private void loadCartData() {
        progressBar.setVisibility(View.VISIBLE);

        recyclerViewCart.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            adapter.updateData();
            updateUI();
        }, 150);
    }

    private void updateUI() {
        if (cartItems == null || cartItems.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            scrollContent.setVisibility(View.GONE);
            layoutSummary.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            scrollContent.setVisibility(View.VISIBLE);
            layoutSummary.setVisibility(View.VISIBLE);
            updateSummary();
        }
    }

    private void updateSummary() {
        double subtotal = 0;
        for (GioHang item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        double shipping = subtotal > 0 ? 30000 : 0;
        double total = subtotal + shipping - discountAmount;

        if (total < 0) {
            total = 0;
        }

        tvSubtotal.setText(formatCurrency(subtotal));
        tvShipping.setText(formatCurrency(shipping));
        tvTotal.setText(formatCurrency(total));

        if (discountAmount > 0) {
            layoutDiscountRow.setVisibility(View.VISIBLE);
            tvDiscount.setText("-" + formatCurrency(discountAmount));
        } else {
            layoutDiscountRow.setVisibility(View.GONE);
            tvDiscount.setText("-0đ");
        }

        kiemTraKhuyenMai();
    }

    private void datHang() {
        if (cartItems == null || cartItems.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
            return;
        }

        int checkedId = radioGroupPayment.getCheckedRadioButtonId();

        if (checkedId == R.id.radioBankTransfer) {
            showBankTransferDialog();
        } else {
            luuDonHang("Tiền mặt");
        }
    }

    private void showBankTransferDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_bank_ck, null);

        TextView tvTransferAmount = dialogView.findViewById(R.id.tvTransferAmount);
        tvTransferAmount.setText(tvTotal.getText().toString());

        dialogView.findViewById(R.id.btnCopyAccount).setOnClickListener(v -> {
            ClipboardManager clipboard =
                    (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Số tài khoản", "999966669999");
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Đã sao chép số tài khoản", Toast.LENGTH_SHORT).show();
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        Button btnConfirmTransfer = dialogView.findViewById(R.id.btnConfirmTransfer);
        Button btnCancelTransfer = dialogView.findViewById(R.id.btnCancelTransfer);

        btnConfirmTransfer.setOnClickListener(v -> {
            dialog.dismiss();
            luuDonHang("Ngân hàng");
        });

        btnCancelTransfer.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void luuDonHang(String paymentMethod) {

        SharedPreferences prefs =
                getSharedPreferences("USER_FILE", MODE_PRIVATE);

        String username =
                prefs.getString("username", "");

        String voucher =
                appliedPromotionCode.isEmpty()
                        ? null
                        : appliedPromotionCode;
        String total =
                tvTotal.getText().toString()
                        .replace(".", "")
                        .replace("đ", "");

        OrderRequest request = new OrderRequest();

        request.setUsername(username);
        request.setVoucherCode(voucher);
        request.setTotalAmount(Double.parseDouble(total));


        ApiService api =
                RetrofitClient.getClient()
                        .create(ApiService.class);

        api.createOrder(request)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {

                        if (response.isSuccessful()) {

                            Toast.makeText(
                                    GioHangActivity.this,
                                    "Đặt hàng thành công",
                                    Toast.LENGTH_SHORT
                            ).show();

                            QuanLyGioHang.getInstance().clearCart();

                            resetKhuyenMai();

                            adapter.updateData();

                            updateUI();

                            finish();

                        } else {

                            Toast.makeText(
                                    GioHangActivity.this,
                                    "Đặt hàng thất bại",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call,
                                          Throwable t) {

                        Toast.makeText(
                                GioHangActivity.this,
                                "Không kết nối được tới server",
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                });

    }

    private String getDanhSachTenSanPham() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < cartItems.size(); i++) {
            GioHang item = cartItems.get(i);
            builder.append(item.getName())
                    .append(" x")
                    .append(item.getQuantity());

            if (i < cartItems.size() - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    private void kiemTraKhuyenMai() {

        if (isPromotionApplied) {
            layoutPromotion.setVisibility(View.GONE);
            return;
        }

        double subtotal = 0;

        for (GioHang item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        ApiService api =
                RetrofitClient.getClient()
                        .create(ApiService.class);

        api.checkPromotion(subtotal)
                .enqueue(new Callback<PromotionAPI>() {

                    @Override
                    public void onResponse(Call<PromotionAPI> call,
                                           Response<PromotionAPI> response) {

                        if(response.isSuccessful()
                                && response.body()!=null){

                            currentVoucher = response.body();

                            txtPromotionHint.setText(
                                    "Gợi ý: Dùng mã "
                                            + currentVoucher.getCode()
                                            + " - giảm "
                                            + formatCurrency(currentVoucher.getDiscount())
                                            + " cho đơn từ "
                                            + formatCurrency(currentVoucher.getMinTotal())
                            );

                            layoutPromotion.setVisibility(View.VISIBLE);

                        }else{

                            layoutPromotion.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onFailure(Call<PromotionAPI> call,
                                          Throwable t) {

                        layoutPromotion.setVisibility(View.GONE);

                    }
                });

    }

    private void apDungKhuyenMai() {

        if(currentVoucher==null){

            Toast.makeText(
                    this,
                    "Không có voucher phù hợp",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        appliedPromotionCode =
                currentVoucher.getCode();

        discountAmount =
                currentVoucher.getDiscount();

        isPromotionApplied = true;

        btnApplyPromotion.setEnabled(false);

        btnApplyPromotion.setText("Đã áp dụng");

        btnApplyPromotion.setAlpha(0.6f);

        updateSummary();

    }

    private void resetKhuyenMai() {
        discountAmount = 0;
        appliedPromotionCode = "";
        isPromotionApplied = false;

        btnApplyPromotion.setEnabled(true);
        btnApplyPromotion.setText("Áp dụng mã");
        btnApplyPromotion.setAlpha(1f);
    }

    @Override
    public void onIncrease(int position, GioHang item) {
        item.setQuantity(item.getQuantity() + 1);
        adapter.notifyItemChanged(position);
        resetKhuyenMai();
        updateSummary();
    }

    @Override
    public void onDecrease(int position, GioHang item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            adapter.notifyItemChanged(position);
            resetKhuyenMai();
            updateSummary();
        }
    }

    @Override
    public void onDelete(int position, GioHang item) {
        QuanLyGioHang.getInstance().removeItem(position);
        adapter.removeItem(position);
        resetKhuyenMai();
        updateUI();
    }

    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        return formatter.format((long) amount) + "đ";
    }
}