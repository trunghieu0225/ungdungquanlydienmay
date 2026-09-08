//package com.example.dienmayapp.activity;
//
//import com.example.btl.R;
//import com.example.dienmayapp.model.Sanpham;
//
//import java.util.ArrayList;
//
//public class DanhMucSanPham {
//
//    public static ArrayList<Sanpham> layTatCaSanPham() {
//        ArrayList<Sanpham> ds = new ArrayList<>();
//
//        ds.add(new Sanpham(
//                "Nagakawa Inverter 1 HP NIS-C09R2T28",
//                "5.490.000đ",
//                "8.690.000đ -36%",
//                "Quà 999.000đ",
//                "⭐ 4.9 • Đã bán 148,9k",
//                R.drawable.dieuhoa_nagawa,
//                "Máy lạnh",
//                "Máy lạnh Nagakawa Inverter 1 HP phù hợp phòng nhỏ dưới 15m2, tiết kiệm điện, vận hành êm và làm lạnh nhanh."
//        ));
//
//        ds.add(new Sanpham(
//                "Midea Inverter 1 HP MAFA-09CDN8",
//                "5.690.000đ",
//                "8.590.000đ -33%",
//                "Online giá rẻ quá",
//                "⭐ 4.9 • Đã bán 48,9k",
//                R.drawable.dieuhoa1,
//                "Máy lạnh",
//                "Máy lạnh Midea Inverter 1 HP có khả năng làm lạnh nhanh, tiết kiệm điện, thiết kế hiện đại và phù hợp phòng nhỏ."
//        ));
//
//        ds.add(new Sanpham(
//                "Daikin Inverter 1 HP ATKF25XVMV",
//                "10.990.000đ",
//                "12.990.000đ -15%",
//                "Quà 500.000đ", Điện máy
//                "⭐ 4.8 • Đã bán 21,3k",
//                R.drawable.dieuhoa2,
//                "Máy lạnh",Điện máy
//                "Máy lạnh Daikin Inverter 1 HP nổi bật với độ bền cao, làm lạnh ổn định, vận hành êm và tiết kiệm điện."
//        ));
//
//        ds.add(new Sanpham(
//                "Samsung Smart TV 43 inch",
//                "8.990.000đ",
//                "10.990.000đ -18%",
//                "Quà 500.000đ",
//                "⭐ 4.8 • Đã bán 20,1k",
//                R.drawable.tv2,
//                "Tivi",
//                "Smart TV Samsung 43 inch cho hình ảnh sắc nét, kết nối internet tiện lợi, phù hợp giải trí gia đình."
//        ));
//
//        ds.add(new Sanpham(
//                "Smart Tivi NanoCell LG AI 4K 50 inch 50NANO80ASA",
//                "15.950.000đ",
//                "13.990.000đ -10%",
//                "Quà 690.000đ",
//                "⭐ 4.7 • Đã bán 11,4k",
//                R.drawable.tv1,
//                "Tivi",
//                "Smart Tivi LG NanoCell 4K 50 inch cho màu sắc sống động, hình ảnh chân thực, tích hợp AI và điều khiển thông minh."
//        ));
//
//        ds.add(new Sanpham(
//                "Bình giữ nhiệt GERM",
//                "490.000đ",
//                "690.000đ -29%",
//                "Quà 50.000đ",
//                "⭐ 4.7 • Đã bán 5,1k",
//                R.drawable.binhnuoc3,
//                "Bình giữ nhiệt",
//                "Bình giữ nhiệt GERM giữ nóng và lạnh tốt, thiết kế gọn nhẹ, thích hợp mang đi học, đi làm hoặc du lịch."
//        ));
//
//        ds.add(new Sanpham(
//                "Bình giữ nhiệt Lock&Lock",
//                "550.000đ",
//                "750.000đ -27%",
//                "Quà 30.000đ",
//                "⭐ 4.8 • Đã bán 7,3k",
//                R.drawable.binhnuoc2,
//                "Bình giữ nhiệt",
//                "Bình giữ nhiệt Lock&Lock có chất liệu bền, an toàn, giữ nhiệt lâu và kiểu dáng hiện đại."
//        ));
//      Đồ bếp
//        ds.add(new Sanpham(
//                "Nồi cơm điện Sharp",
//                "1.290.000đ",
//                "1.590.000đ -19%",
//                "Quà 100.000đ",
//                "⭐ 4.6 • Đã bán 4,2k",
//                R.drawable.noicom1,
//                "Gia dụng",
//                "Nồi cơm điện Sharp nấu cơm nhanh, giữ ấm tốt, dễ sử dụng và phù hợp cho gia đình nhỏ."
//        ));
//
//        ds.add(new Sanpham(
//                "Ấm siêu tốc Sunhouse",
//                "390.000đ",
//                "520.000đ -25%",
//                "Quà 20.000đ",
//                "⭐ 4.5 • Đã bán 6,8k",
//                R.drawable.amnuoc1,
//                "Gia dụng",
//                "Ấm siêu tốc Sunhouse đun nước nhanh, dung tích phù hợp gia đình, thiết kế gọn gàng và dễ vệ sinh."
//        ));
//
//        return ds;
//    }
//
//    public static ArrayList<Sanpham> laySanPhamTheoLoai(String loai) {
//        ArrayList<Sanpham> tatCa = layTatCaSanPham();
//        ArrayList<Sanpham> ketQua = new ArrayList<>();
//
//        if (loai.equals("Tất cả")) {
//            return tatCa;
//        }
//
//        for (Sanpham sp : tatCa) {
//            if (sp.getLoai().equals(loai)) {
//                ketQua.add(sp);
//            }
//        }
//
//        return ketQua;
//    }
//}