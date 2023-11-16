package com.thuydev.pro1121appbangiay.model;

public class ThongBao {
    private String maThongBao;
    private String maKhachHang;
    private String noiDung;
    private String chucVu;


    public ThongBao() {
    }

    public ThongBao(String maThongBao, String maKhachHang, String noiDung, String chucVu) {
        this.maThongBao = maThongBao;
        this.maKhachHang = maKhachHang;
        this.noiDung = noiDung;
        this.chucVu = chucVu;
    }

    public String getMaThongBao() {
        return maThongBao;
    }

    public void setMaThongBao(String maThongBao) {
        this.maThongBao = maThongBao;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}
