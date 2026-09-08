package com.example.dienmayapp.model;

public class Sanpham {
    private String Ten;
    private String Gia;
    private String GiaCu;
    private String Qua;
    private String DanhGia;
    private String Anh;
    private String Loai;
    private String MoTa;

    public Sanpham(String ten, String gia, String giaCu, String qua, String danhGia, String anh, String loai, String moTa) {
        Ten = ten;
        Gia = gia;
        GiaCu = giaCu;
        Qua = qua;
        DanhGia = danhGia;
        Anh = anh;
        Loai = loai;
        MoTa = moTa;
    }

    public String getTen() {
        return Ten;
    }

    public String getGia() {
        return Gia;
    }

    public String getGiaCu() {
        return GiaCu;
    }

    public String getQua() {
        return Qua;
    }

    public String getDanhGia() {
        return DanhGia;
    }

    public String getAnh() {
        return Anh;
    }

    public String getLoai() {
        return Loai;
    }

    public String getMoTa() {
        return MoTa;
    }
}