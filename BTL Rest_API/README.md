# 📱 ỨNG DỤNG BÁN HÀNG ĐIỆN MÁY (ANDROID CLIENT)

> Ứng dụng Android Native kết nối với Backend **Server_API_DienMay** qua **Retrofit RESTful API**.

---

## 🛠️ Công nghệ sử dụng
- **Ngôn ngữ**: Java (Android SDK).
- **Mạng**: Retrofit 2 (`com.squareup.retrofit2:retrofit:2.9.0`).
- **Chuyển đổi dữ liệu**: Gson Converter (`com.squareup.retrofit2:converter-gson:2.9.0`).
- **Giao diện**: RecyclerView, CardView, Material Design.
- **Lưu trữ cục bộ**: Android `SharedPreferences` (Lưu phiên đăng nhập `USER_FILE`).

---

## ⚙️ Cấu hình địa chỉ IP kết nối Server (`BASE_URL`)

Mở file:  
[`app/src/main/java/com/example/dienmayapp/api/RetrofitClient.java`](file:///d:/Study/2025-2026/KT&TK%20software/BTL%20Rest_API/app/src/main/java/com/example/dienmayapp/api/RetrofitClient.java)

```java
public class RetrofitClient {
    // Dành cho máy ảo Android Studio (Emulator)
    private static final String BASE_URL =
            "http://10.0.2.2:8080/Server_API_DienMay-1.0-SNAPSHOT/api/";

    // Nếu chạy trên ĐIỆN THOẠI THẬT (kết nối chung mạng Wi-Fi với máy tính):
    // Thay 10.0.2.2 bằng IP LAN của máy tính (xem bằng lệnh ipconfig)
    // Ví dụ: "http://192.168.1.10:8080/Server_API_DienMay-1.0-SNAPSHOT/api/";
}
```

---

## 🚀 Hướng dẫn chạy ứng dụng
1. Đảm bảo **MySQL (cổng 3307)** và **WildFly Server (cổng 8080)** đang chạy.
2. Mở thư mục này bằng **Android Studio**.
3. Đợi Gradle Sync hoàn tất.
4. Bật máy ảo Android (Emulator) hoặc cắm điện thoại đã bật chế độ *USB Debugging*.
5. Bấm nút **Run ▶️** (`Shift + F10`) để cài đặt và chạy ứng dụng.
