package com.example.dienmayapp.activity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.R;
import com.example.dienmayapp.database.DatabaseHelper;
import com.example.dienmayapp.database.DatabaseManager;

import java.util.ArrayList;

public class QuanLyDonHang extends AppCompatActivity {

    ListView lvOrders;
    Button btnAddOrder, btnEditOrder, btnDeleteOrder, btnConfirmOrder, btnViewOrderDetail;

    DatabaseManager databaseManager;
    ArrayAdapter<String> orderAdapter;
    ArrayList<String> orderList;

    int selectedOrderId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanly_donhang);

        lvOrders = findViewById(R.id.lvOrders);
        btnAddOrder = findViewById(R.id.btnAddOrder);
        btnEditOrder = findViewById(R.id.btnEditOrder);
        btnDeleteOrder = findViewById(R.id.btnDeleteOrder);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        btnViewOrderDetail = findViewById(R.id.btnViewOrderDetail);

        databaseManager = new DatabaseManager(this);

        loadOrders();

        lvOrders.setOnItemClickListener((parent, view, position, id) -> {
            String item = orderList.get(position);
            String[] parts = item.split("\\|");
            if (parts.length >= 5) {
                try {
                    selectedOrderId = Integer.parseInt(parts[0].trim());
                    Toast.makeText(this, "Đã chọn đơn hàng ID: " + selectedOrderId, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    selectedOrderId = -1;
                    Toast.makeText(this, "Không đọc được ID đơn hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddOrder.setOnClickListener(v -> showOrderDialog(false));

        btnEditOrder.setOnClickListener(v -> {
            if (selectedOrderId == -1) {
                Toast.makeText(this, "Hãy chọn 1 đơn hàng để sửa", Toast.LENGTH_SHORT).show();
                return;
            }
            showOrderDialog(true);
        });

        btnDeleteOrder.setOnClickListener(v -> {
            if (selectedOrderId == -1) {
                Toast.makeText(this, "Hãy chọn 1 đơn hàng để xóa", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Xóa đơn hàng")
                    .setMessage("Bạn có chắc muốn xóa đơn hàng này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        boolean result = databaseManager.deleteOrder(selectedOrderId);
                        if (result) {
                            Toast.makeText(this, "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            selectedOrderId = -1;
                            loadOrders();
                        } else {
                            Toast.makeText(this, "Xóa đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        btnConfirmOrder.setOnClickListener(v -> {
            if (selectedOrderId == -1) {
                Toast.makeText(this, "Hãy chọn 1 đơn hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result = databaseManager.confirmOrder(selectedOrderId);
            if (result) {
                Toast.makeText(this, "Xác nhận đơn thành công", Toast.LENGTH_SHORT).show();
                loadOrders();
            } else {
                Toast.makeText(this, "Xác nhận đơn thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewOrderDetail.setOnClickListener(v -> {
            if (selectedOrderId == -1) {
                Toast.makeText(this, "Hãy chọn 1 đơn hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(QuanLyDonHang.this, ChiTietDonHang.class);
            intent.putExtra("order_id", selectedOrderId);
            startActivity(intent);
        });
    }

    private void loadOrders() {
        orderList = databaseManager.getAllOrders();
        orderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderList);
        lvOrders.setAdapter(orderAdapter);
    }

    private void showOrderDialog(boolean isEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isEdit ? "Sửa đơn hàng" : "Thêm đơn hàng");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = 24;
        layout.setPadding(pad, pad, pad, pad);

        EditText edtCustomerName = new EditText(this);
        edtCustomerName.setHint("Tên khách hàng");

        EditText edtProductName = new EditText(this);
        edtProductName.setHint("Tên sản phẩm");

        EditText edtProductPrice = new EditText(this);
        edtProductPrice.setHint("Giá sản phẩm");

        EditText edtPaymentMethod = new EditText(this);
        edtPaymentMethod.setHint("Phương thức thanh toán");

        EditText edtVoucher = new EditText(this);
        edtVoucher.setHint("Voucher");

        EditText edtPrice = new EditText(this);
        edtPrice.setHint("Giá");

        EditText edtTotal = new EditText(this);
        edtTotal.setHint("Tổng tiền");

        EditText edtStatus = new EditText(this);
        edtStatus.setHint("Trạng thái");

        edtProductPrice.setInputType(InputType.TYPE_CLASS_TEXT);
        edtPrice.setInputType(InputType.TYPE_CLASS_TEXT);
        edtTotal.setInputType(InputType.TYPE_CLASS_TEXT);

        layout.addView(edtCustomerName);
        layout.addView(edtProductName);
        layout.addView(edtProductPrice);
        layout.addView(edtPaymentMethod);
        layout.addView(edtVoucher);
        layout.addView(edtPrice);
        layout.addView(edtTotal);
        layout.addView(edtStatus);

        if (isEdit) {
            Cursor cursor = databaseManager.getOrderDetailById(selectedOrderId);
            if (cursor != null && cursor.moveToFirst()) {
                edtCustomerName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_CUSTOMER_NAME)));
                edtProductName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRODUCT_NAME)));
                edtProductPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRODUCT_PRICE)));
                edtPaymentMethod.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PAYMENT_METHOD)));
                edtVoucher.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_VOUCHER)));
                edtPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRICE)));
                edtTotal.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_TOTAL)));
                edtStatus.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_STATUS)));
            }
            if (cursor != null) cursor.close();
        } else {
            edtStatus.setText("Chờ xác nhận");
        }

        builder.setView(layout);

        builder.setPositiveButton(isEdit ? "Lưu" : "Thêm", (dialog, which) -> {
            String customerName = edtCustomerName.getText().toString().trim();
            String productName = edtProductName.getText().toString().trim();
            String productPrice = edtProductPrice.getText().toString().trim();
            String paymentMethod = edtPaymentMethod.getText().toString().trim();
            String voucher = edtVoucher.getText().toString().trim();
            String price = edtPrice.getText().toString().trim();
            String total = edtTotal.getText().toString().trim();
            String status = edtStatus.getText().toString().trim();

            if (customerName.isEmpty() || productName.isEmpty() || productPrice.isEmpty()
                    || paymentMethod.isEmpty() || price.isEmpty() || total.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean result;
            if (isEdit) {
                result = databaseManager.updateOrder(
                        selectedOrderId,
                        customerName,
                        productName,
                        productPrice,
                        paymentMethod,
                        voucher,
                        price,
                        total,
                        status
                );
            } else {
                result = databaseManager.addOrder(
                        customerName,
                        productName,
                        productPrice,
                        paymentMethod,
                        voucher,
                        price,
                        total
                );
            }

            if (result) {
                Toast.makeText(this, isEdit ? "Sửa đơn hàng thành công" : "Thêm đơn hàng thành công", Toast.LENGTH_SHORT).show();
                selectedOrderId = -1;
                loadOrders();
            } else {
                Toast.makeText(this, isEdit ? "Sửa đơn hàng thất bại" : "Thêm đơn hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}