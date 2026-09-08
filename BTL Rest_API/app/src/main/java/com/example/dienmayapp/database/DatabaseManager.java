package com.example.dienmayapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.R;
import com.example.dienmayapp.model.DonHangItem;
import com.example.dienmayapp.model.Sanpham;

import java.util.ArrayList;

public class DatabaseManager {

    private final DatabaseHelper dbHelper;
    private final Context context;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // ===== USER =====
    public boolean registerCustomer(String username, String password, String fullname) {
        open();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.USERNAME + "=?",
                new String[]{username}
        );

        if (cursor.getCount() > 0) {
            cursor.close();
            close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USERNAME, username);
        values.put(DatabaseHelper.PASSWORD, password);
        values.put(DatabaseHelper.FULLNAME, fullname);
        values.put(DatabaseHelper.ROLE, "customer");

        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        close();
        return result != -1;
    }

    public String loginUser(String username, String password) {
        open();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                        " WHERE " + DatabaseHelper.USERNAME + "=? AND " + DatabaseHelper.PASSWORD + "=?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            String role = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROLE));
            cursor.close();
            close();
            return role;
        }

        cursor.close();
        close();
        return null;
    }

    public String getFullName(String username) {
        open();

        Cursor cursor = db.rawQuery(
                "SELECT " + DatabaseHelper.FULLNAME + " FROM " + DatabaseHelper.TABLE_USERS +
                        " WHERE " + DatabaseHelper.USERNAME + "=?",
                new String[]{username}
        );

        if (cursor.moveToFirst()) {
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FULLNAME));
            cursor.close();
            close();
            return fullname;
        }

        cursor.close();
        close();
        return "";
    }

    public boolean addUser(String username, String password, String fullname, String role) {
        open();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.USERNAME + "=?",
                new String[]{username}
        );

        if (cursor.getCount() > 0) {
            cursor.close();
            close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USERNAME, username);
        values.put(DatabaseHelper.PASSWORD, password);
        values.put(DatabaseHelper.FULLNAME, fullname);
        values.put(DatabaseHelper.ROLE, role);

        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        close();
        return result != -1;
    }

    public boolean updateUser(int id, String username, String password, String fullname, String role) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USERNAME, username);
        values.put(DatabaseHelper.PASSWORD, password);
        values.put(DatabaseHelper.FULLNAME, fullname);
        values.put(DatabaseHelper.ROLE, role);

        int result = db.update(
                DatabaseHelper.TABLE_USERS,
                values,
                DatabaseHelper.USER_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public boolean deleteUser(int id) {
        open();

        int result = db.delete(
                DatabaseHelper.TABLE_USERS,
                DatabaseHelper.USER_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public ArrayList<String> getAllUsers() {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                        " ORDER BY " + DatabaseHelper.USER_ID + " DESC",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FULLNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROLE));

            list.add(id + " | " + username + " | " + password + " | " + fullname + " | " + role);
        }

        cursor.close();
        close();
        return list;
    }

    public ArrayList<String> searchUsers(String keyword) {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_USERS +
                        " WHERE " + DatabaseHelper.USERNAME + " LIKE ? OR " +
                        DatabaseHelper.FULLNAME + " LIKE ? OR " +
                        DatabaseHelper.ROLE + " LIKE ? " +
                        " ORDER BY " + DatabaseHelper.USER_ID + " DESC",
                new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FULLNAME));
            String role = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROLE));

            list.add(id + " | " + username + " | " + password + " | " + fullname + " | " + role);
        }

        cursor.close();
        close();
        return list;
    }

    // ===== CATEGORY =====
    public boolean addCategory(String name, String desc) {
        open();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORIES + " WHERE " + DatabaseHelper.CATEGORY_NAME + "=?",
                new String[]{name}
        );

        if (cursor.getCount() > 0) {
            cursor.close();
            close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATEGORY_NAME, name);
        values.put(DatabaseHelper.CATEGORY_DESC, desc);

        long result = db.insert(DatabaseHelper.TABLE_CATEGORIES, null, values);
        close();
        return result != -1;
    }

    public boolean updateCategory(int id, String name, String desc) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CATEGORY_NAME, name);
        values.put(DatabaseHelper.CATEGORY_DESC, desc);

        int result = db.update(
                DatabaseHelper.TABLE_CATEGORIES,
                values,
                DatabaseHelper.CATEGORY_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public boolean deleteCategory(int id) {
        open();

        int result = db.delete(
                DatabaseHelper.TABLE_CATEGORIES,
                DatabaseHelper.CATEGORY_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public ArrayList<String> getAllCategories() {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORIES +
                        " ORDER BY " + DatabaseHelper.CATEGORY_ID + " DESC",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_NAME));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_DESC));

            list.add(id + " | " + name + " | " + desc);
        }

        cursor.close();
        close();
        return list;
    }

    public ArrayList<String> searchCategories(String keyword) {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORIES +
                        " WHERE " + DatabaseHelper.CATEGORY_NAME + " LIKE ? OR " +
                        DatabaseHelper.CATEGORY_DESC + " LIKE ? " +
                        " ORDER BY " + DatabaseHelper.CATEGORY_ID + " DESC",
                new String[]{"%" + keyword + "%", "%" + keyword + "%"}
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_NAME));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_DESC));

            list.add(id + " | " + name + " | " + desc);
        }

        cursor.close();
        close();
        return list;
    }

    public ArrayList<String> getCategoryNames() {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT " + DatabaseHelper.CATEGORY_NAME + " FROM " + DatabaseHelper.TABLE_CATEGORIES +
                        " ORDER BY " + DatabaseHelper.CATEGORY_NAME + " ASC",
                null
        );

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CATEGORY_NAME)));
        }

        cursor.close();
        close();
        return list;
    }

    // ===== PRODUCT =====
    public boolean addProduct(String ten, String gia, String giaCu, String qua,
                              String danhGia, String anh, String loai, String moTa) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PRODUCT_TEN, ten);
        values.put(DatabaseHelper.PRODUCT_GIA, gia);
        values.put(DatabaseHelper.PRODUCT_GIA_CU, giaCu);
        values.put(DatabaseHelper.PRODUCT_QUA, qua);
        values.put(DatabaseHelper.PRODUCT_DANH_GIA, danhGia);
        values.put(DatabaseHelper.PRODUCT_ANH, anh);
        values.put(DatabaseHelper.PRODUCT_LOAI, loai);
        values.put(DatabaseHelper.PRODUCT_MO_TA, moTa);

        long result = db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
        close();
        return result != -1;
    }

    public boolean updateProduct(int id, String ten, String gia, String giaCu, String qua,
                                 String danhGia, String anh, String loai, String moTa) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PRODUCT_TEN, ten);
        values.put(DatabaseHelper.PRODUCT_GIA, gia);
        values.put(DatabaseHelper.PRODUCT_GIA_CU, giaCu);
        values.put(DatabaseHelper.PRODUCT_QUA, qua);
        values.put(DatabaseHelper.PRODUCT_DANH_GIA, danhGia);
        values.put(DatabaseHelper.PRODUCT_ANH, anh);
        values.put(DatabaseHelper.PRODUCT_LOAI, loai);
        values.put(DatabaseHelper.PRODUCT_MO_TA, moTa);

        int result = db.update(
                DatabaseHelper.TABLE_PRODUCTS,
                values,
                DatabaseHelper.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public boolean deleteProduct(int id) {
        open();

        int result = db.delete(
                DatabaseHelper.TABLE_PRODUCTS,
                DatabaseHelper.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public ArrayList<String> getAllProducts() {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS +
                        " ORDER BY " + DatabaseHelper.PRODUCT_ID + " DESC",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_ID));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_TEN));
            String gia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_GIA));
            String giaCu = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_GIA_CU));
            String qua = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_QUA));
            String danhGia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_DANH_GIA));
            String anh = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_ANH));
            String loai = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_LOAI));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_MO_TA));

            list.add(id + " | " + ten + " | " + gia + " | " + giaCu + " | " + qua + " | " + danhGia + " | " + anh + " | " + loai + " | " + moTa);
        }

        cursor.close();
        close();
        return list;
    }

    public ArrayList<String> searchProducts(String keyword) {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS +
                        " WHERE " + DatabaseHelper.PRODUCT_TEN + " LIKE ? OR " +
                        DatabaseHelper.PRODUCT_LOAI + " LIKE ? OR " +
                        DatabaseHelper.PRODUCT_MO_TA + " LIKE ? " +
                        " ORDER BY " + DatabaseHelper.PRODUCT_ID + " DESC",
                new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_ID));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_TEN));
            String gia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_GIA));
            String giaCu = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_GIA_CU));
            String qua = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_QUA));
            String danhGia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_DANH_GIA));
            String anh = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_ANH));
            String loai = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_LOAI));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_MO_TA));

            list.add(id + " | " + ten + " | " + gia + " | " + giaCu + " | " + qua + " | " + danhGia + " | " + anh + " | " + loai + " | " + moTa);
        }

        cursor.close();
        close();
        return list;
    }

    public ArrayList<Sanpham> getAllProductsForUser() {
        open();
        ArrayList<Sanpham> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS +
                        " ORDER BY " + DatabaseHelper.PRODUCT_ID + " DESC",
                null
        );

        while (cursor.moveToNext()) {
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_TEN));
            String gia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_GIA));
            String giaCu = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_GIA_CU));
            String qua = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_QUA));
            String danhGia = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_DANH_GIA));
            String anhStr = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_ANH));
            String loai = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_LOAI));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PRODUCT_MO_TA));

            int anhRes = R.drawable.dieuhoa_nagawa;

            if (anhStr != null && !anhStr.isEmpty()) {
                if (!anhStr.startsWith("content://") && !anhStr.startsWith("file://")) {
                    int resId = context.getResources().getIdentifier(
                            anhStr,
                            "drawable",
                            context.getPackageName()
                    );
                    if (resId != 0) {
                        anhRes = resId;
                    }
                }
            }

       //     list.add(new Sanpham(ten, gia, giaCu, qua, danhGia, anhRes, loai, moTa));
        }

        cursor.close();
        close();
        return list;
    }

    // ===== ORDER =====
    public boolean addOrder(String customerName, String productName, String productPrice,
                            String paymentMethod, String voucher, String price, String total) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ORDER_CUSTOMER_NAME, customerName);
        values.put(DatabaseHelper.ORDER_PRODUCT_NAME, productName);
        values.put(DatabaseHelper.ORDER_PRODUCT_PRICE, productPrice);
        values.put(DatabaseHelper.ORDER_PAYMENT_METHOD, paymentMethod);
        values.put(DatabaseHelper.ORDER_VOUCHER, voucher);
        values.put(DatabaseHelper.ORDER_PRICE, price);
        values.put(DatabaseHelper.ORDER_TOTAL, total);
        values.put(DatabaseHelper.ORDER_STATUS, "Chờ xác nhận");

        long result = db.insert(DatabaseHelper.TABLE_ORDERS, null, values);
        close();
        return result != -1;
    }

    public boolean updateOrder(int orderId, String customerName, String productName, String productPrice,
                               String paymentMethod, String voucher, String price, String total, String status) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ORDER_CUSTOMER_NAME, customerName);
        values.put(DatabaseHelper.ORDER_PRODUCT_NAME, productName);
        values.put(DatabaseHelper.ORDER_PRODUCT_PRICE, productPrice);
        values.put(DatabaseHelper.ORDER_PAYMENT_METHOD, paymentMethod);
        values.put(DatabaseHelper.ORDER_VOUCHER, voucher);
        values.put(DatabaseHelper.ORDER_PRICE, price);
        values.put(DatabaseHelper.ORDER_TOTAL, total);
        values.put(DatabaseHelper.ORDER_STATUS, status);

        int result = db.update(
                DatabaseHelper.TABLE_ORDERS,
                values,
                DatabaseHelper.ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)}
        );

        close();
        return result > 0;
    }

    public boolean deleteOrder(int orderId) {
        open();

        int result = db.delete(
                DatabaseHelper.TABLE_ORDERS,
                DatabaseHelper.ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)}
        );

        close();
        return result > 0;
    }

    public ArrayList<DonHangItem> getOrdersByCustomer(String customerName) {
        open();
        ArrayList<DonHangItem> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_ORDERS +
                        " WHERE " + DatabaseHelper.ORDER_CUSTOMER_NAME + "=? " +
                        " ORDER BY " + DatabaseHelper.ORDER_ID + " DESC",
                new String[]{customerName}
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_ID));
            String tenKhach = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_CUSTOMER_NAME));
            String tenSanPham = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRODUCT_NAME));
            String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PAYMENT_METHOD));
            String voucher = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_VOUCHER));
            String price = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRICE));
            String total = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_TOTAL));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_STATUS));

            list.add(new DonHangItem(id, tenKhach, tenSanPham, paymentMethod, voucher, price, total, status));
        }

        cursor.close();
        close();
        return list;
    }

    public boolean cancelOrder(int orderId) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ORDER_STATUS, "Đã huỷ");

        int result = db.update(
                DatabaseHelper.TABLE_ORDERS,
                values,
                DatabaseHelper.ORDER_ID + "=? AND " + DatabaseHelper.ORDER_STATUS + "=?",
                new String[]{String.valueOf(orderId), "Chờ xác nhận"}
        );

        close();
        return result > 0;
    }

    public ArrayList<String> getAllOrders() {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_ORDERS +
                        " ORDER BY " + DatabaseHelper.ORDER_ID + " DESC",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_ID));
            String customerName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_CUSTOMER_NAME));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_PRODUCT_NAME));
            String total = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_TOTAL));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ORDER_STATUS));

            list.add(id + " | " + customerName + " | " + productName + " | " + total + " | " + status);
        }

        cursor.close();
        close();
        return list;
    }

    public boolean confirmOrder(int orderId) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ORDER_STATUS, "Đã xác nhận");

        int result = db.update(
                DatabaseHelper.TABLE_ORDERS,
                values,
                DatabaseHelper.ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)}
        );

        close();
        return result > 0;
    }

    public Cursor getOrderDetailById(int orderId) {
        open();

        return db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_ORDERS +
                        " WHERE " + DatabaseHelper.ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)}
        );
    }

    // ===== PROMOTION =====
    public boolean addPromotion(String code, double discountAmount, double minOrderTotal) {
        open();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_PROMOTIONS +
                        " WHERE " + DatabaseHelper.PROMOTION_CODE + "=?",
                new String[]{code}
        );

        if (cursor.getCount() > 0) {
            cursor.close();
            close();
            return false;
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PROMOTION_CODE, code);
        values.put(DatabaseHelper.PROMOTION_DISCOUNT, discountAmount);
        values.put(DatabaseHelper.PROMOTION_MIN_TOTAL, minOrderTotal);

        long result = db.insert(DatabaseHelper.TABLE_PROMOTIONS, null, values);
        close();
        return result != -1;
    }

    public boolean updatePromotion(int id, String code, double discountAmount, double minOrderTotal) {
        open();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PROMOTION_CODE, code);
        values.put(DatabaseHelper.PROMOTION_DISCOUNT, discountAmount);
        values.put(DatabaseHelper.PROMOTION_MIN_TOTAL, minOrderTotal);

        int result = db.update(
                DatabaseHelper.TABLE_PROMOTIONS,
                values,
                DatabaseHelper.PROMOTION_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public boolean deletePromotion(int id) {
        open();

        int result = db.delete(
                DatabaseHelper.TABLE_PROMOTIONS,
                DatabaseHelper.PROMOTION_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        close();
        return result > 0;
    }

    public ArrayList<String> getAllPromotions() {
        open();
        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_PROMOTIONS +
                        " ORDER BY " + DatabaseHelper.PROMOTION_ID + " DESC",
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PROMOTION_ID));
            String code = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROMOTION_CODE));
            double discount = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.PROMOTION_DISCOUNT));
            double minTotal = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.PROMOTION_MIN_TOTAL));

            list.add(id + " | " + code + " | " + discount + " | " + minTotal);
        }

        cursor.close();
        close();
        return list;
    }

    public Cursor getBestPromotion(double totalMoney) {
        open();

        return db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_PROMOTIONS +
                        " WHERE " + DatabaseHelper.PROMOTION_MIN_TOTAL + "<=? " +
                        " ORDER BY " + DatabaseHelper.PROMOTION_DISCOUNT + " DESC LIMIT 1",
                new String[]{String.valueOf(totalMoney)}
        );
    }
}