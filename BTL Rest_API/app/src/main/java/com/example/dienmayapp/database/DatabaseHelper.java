package com.example.dienmayapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dienmayxanh.db";
    public static final int DATABASE_VERSION = 9;

    // USERS
    public static final String TABLE_USERS = "users";
    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String FULLNAME = "fullname";
    public static final String ROLE = "role";

    // PRODUCTS
    public static final String TABLE_PRODUCTS = "products";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_TEN = "ten";
    public static final String PRODUCT_GIA = "gia";
    public static final String PRODUCT_GIA_CU = "gia_cu";
    public static final String PRODUCT_QUA = "qua";
    public static final String PRODUCT_DANH_GIA = "danh_gia";
    public static final String PRODUCT_ANH = "anh";
    public static final String PRODUCT_LOAI = "loai";
    public static final String PRODUCT_MO_TA = "mo_ta";

    // CATEGORIES
    public static final String TABLE_CATEGORIES = "categories";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESC = "category_desc";

    // ORDERS
    public static final String TABLE_ORDERS = "orders";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_CUSTOMER_NAME = "customer_name";
    public static final String ORDER_PRODUCT_NAME = "product_name";
    public static final String ORDER_PRODUCT_PRICE = "product_price";
    public static final String ORDER_PAYMENT_METHOD = "payment_method";
    public static final String ORDER_VOUCHER = "voucher";
    public static final String ORDER_PRICE = "price";
    public static final String ORDER_TOTAL = "total";
    public static final String ORDER_STATUS = "status";

    // PROMOTIONS
    public static final String TABLE_PROMOTIONS = "promotions";
    public static final String PROMOTION_ID = "promotion_id";
    public static final String PROMOTION_CODE = "code";
    public static final String PROMOTION_DISCOUNT = "discount_amount";
    public static final String PROMOTION_MIN_TOTAL = "min_order_total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsers = "CREATE TABLE " + TABLE_USERS + " ("
                + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT UNIQUE, "
                + PASSWORD + " TEXT, "
                + FULLNAME + " TEXT, "
                + ROLE + " TEXT)";

        String createProducts = "CREATE TABLE " + TABLE_PRODUCTS + " ("
                + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_TEN + " TEXT, "
                + PRODUCT_GIA + " TEXT, "
                + PRODUCT_GIA_CU + " TEXT, "
                + PRODUCT_QUA + " TEXT, "
                + PRODUCT_DANH_GIA + " TEXT, "
                + PRODUCT_ANH + " TEXT, "
                + PRODUCT_LOAI + " TEXT, "
                + PRODUCT_MO_TA + " TEXT)";

        String createCategories = "CREATE TABLE " + TABLE_CATEGORIES + " ("
                + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT UNIQUE, "
                + CATEGORY_DESC + " TEXT)";

        String createOrders = "CREATE TABLE " + TABLE_ORDERS + " ("
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_CUSTOMER_NAME + " TEXT, "
                + ORDER_PRODUCT_NAME + " TEXT, "
                + ORDER_PRODUCT_PRICE + " TEXT, "
                + ORDER_PAYMENT_METHOD + " TEXT, "
                + ORDER_VOUCHER + " TEXT, "
                + ORDER_PRICE + " TEXT, "
                + ORDER_TOTAL + " TEXT, "
                + ORDER_STATUS + " TEXT)";

        String createPromotions = "CREATE TABLE " + TABLE_PROMOTIONS + " ("
                + PROMOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PROMOTION_CODE + " TEXT UNIQUE, "
                + PROMOTION_DISCOUNT + " REAL, "
                + PROMOTION_MIN_TOTAL + " REAL)";

        db.execSQL(createUsers);
        db.execSQL(createProducts);
        db.execSQL(createCategories);
        db.execSQL(createOrders);
        db.execSQL(createPromotions);

        ContentValues admin = new ContentValues();
        admin.put(USERNAME, "admin");
        admin.put(PASSWORD, "123456");
        admin.put(FULLNAME, "Quản trị viên");
        admin.put(ROLE, "admin");
        db.insert(TABLE_USERS, null, admin);

        insertDefaultCategories(db);
        insertSampleOrder(db);
    }

    private void insertDefaultCategories(SQLiteDatabase db) {
        insertCategory(db, "Máy lạnh", "Nhóm sản phẩm máy lạnh");
        insertCategory(db, "Tivi", "Nhóm sản phẩm tivi");
        insertCategory(db, "Bình giữ nhiệt", "Nhóm sản phẩm bình giữ nhiệt");
        insertCategory(db, "Gia dụng", "Nhóm sản phẩm gia dụng");
    }

    private void insertCategory(SQLiteDatabase db, String name, String desc) {
        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME, name);
        values.put(CATEGORY_DESC, desc);
        db.insert(TABLE_CATEGORIES, null, values);
    }

    private void insertSampleOrder(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ORDER_CUSTOMER_NAME, "Nguyễn Văn A");
        values.put(ORDER_PRODUCT_NAME, "Tivi Samsung 43 inch");
        values.put(ORDER_PRODUCT_PRICE, "8990000");
        values.put(ORDER_PAYMENT_METHOD, "COD");
        values.put(ORDER_VOUCHER, "SALE100");
        values.put(ORDER_PRICE, "8990000");
        values.put(ORDER_TOTAL, "8890000");
        values.put(ORDER_STATUS, "Chờ xác nhận");
        db.insert(TABLE_ORDERS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMOTIONS);

        String createProducts = "CREATE TABLE " + TABLE_PRODUCTS + " ("
                + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_TEN + " TEXT, "
                + PRODUCT_GIA + " TEXT, "
                + PRODUCT_GIA_CU + " TEXT, "
                + PRODUCT_QUA + " TEXT, "
                + PRODUCT_DANH_GIA + " TEXT, "
                + PRODUCT_ANH + " TEXT, "
                + PRODUCT_LOAI + " TEXT, "
                + PRODUCT_MO_TA + " TEXT)";

        String createCategories = "CREATE TABLE " + TABLE_CATEGORIES + " ("
                + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT UNIQUE, "
                + CATEGORY_DESC + " TEXT)";

        String createOrders = "CREATE TABLE " + TABLE_ORDERS + " ("
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_CUSTOMER_NAME + " TEXT, "
                + ORDER_PRODUCT_NAME + " TEXT, "
                + ORDER_PRODUCT_PRICE + " TEXT, "
                + ORDER_PAYMENT_METHOD + " TEXT, "
                + ORDER_VOUCHER + " TEXT, "
                + ORDER_PRICE + " TEXT, "
                + ORDER_TOTAL + " TEXT, "
                + ORDER_STATUS + " TEXT)";

        String createPromotions = "CREATE TABLE " + TABLE_PROMOTIONS + " ("
                + PROMOTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PROMOTION_CODE + " TEXT UNIQUE, "
                + PROMOTION_DISCOUNT + " REAL, "
                + PROMOTION_MIN_TOTAL + " REAL)";

        db.execSQL(createProducts);
        db.execSQL(createCategories);
        db.execSQL(createOrders);
        db.execSQL(createPromotions);

        insertDefaultCategories(db);
        insertSampleOrder(db);
    }
}