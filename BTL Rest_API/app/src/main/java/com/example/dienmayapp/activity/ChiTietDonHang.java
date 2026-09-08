package com.example.dienmayapp.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.example.dienmayapp.database.DatabaseManager;
import com.example.dienmayapp.database.DatabaseHelper;
import com.example.btl.R;
import androidx.appcompat.app.AppCompatActivity;
public class ChiTietDonHang extends AppCompatActivity {

    TextView txtCustomerName, txtProductName, txtProductPrice, txtPaymentMethod,
            txtVoucher, txtPrice, txtTotal, txtStatus;

    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_chitiet_donhang);

        txtCustomerName = findViewById(R.id.txtCustomerName);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        txtPaymentMethod = findViewById(R.id.txtPaymentMethod);
        txtVoucher = findViewById(R.id.txtVoucher);
        txtPrice = findViewById(R.id.txtPrice);
        txtTotal = findViewById(R.id.txtTotal);
        txtStatus = findViewById(R.id.txtStatus);

        databaseManager = new DatabaseManager(this);

        int orderId = getIntent().getIntExtra("order_id", -1);

        if (orderId != -1) {
            loadOrderDetail(orderId);
        }
    }

    private void loadOrderDetail(int orderId) {
        Cursor cursor = databaseManager.getOrderDetailById(orderId);

        if (cursor != null && cursor.moveToFirst()) {
            txtCustomerName.setText("Khách hàng: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_CUSTOMER_NAME)));
            txtProductName.setText("Tên sản phẩm: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRODUCT_NAME)));
            txtProductPrice.setText("Giá sản phẩm: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRODUCT_PRICE)));
            txtPaymentMethod.setText("Phương thức thanh toán: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PAYMENT_METHOD)));
            txtVoucher.setText("Voucher: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_VOUCHER)));
            txtPrice.setText("Giá: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRICE)));
            txtTotal.setText("Tổng tiền: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_TOTAL)));
            txtStatus.setText("Trạng thái: " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_STATUS)));
        }

        if (cursor != null) cursor.close();
        databaseManager.close();
    }
}

