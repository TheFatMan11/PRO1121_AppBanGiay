package com.thuydev.pro1121appbangiay.model;

import java.util.HashMap;
import java.util.List;

public class SanPham {
    private String maSp;
    private String anh;
    private String tenSP;
    private Long gia;
    private Long soLuong;
    private String maHang;
    private List<String> kichCo;
    private String namSX;
    private Long time;

    public SanPham() {
    }

    public SanPham(String maSp, String anh, String tenSP, Long gia, Long soLuong, String maHang, List<String> kichCo, String namSX, Long time) {
        this.maSp = maSp;
        this.anh = anh;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.maHang = maHang;
        this.kichCo = kichCo;
        this.namSX = namSX;
        this.time = time;
    }


    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Long getGia() {
        return gia;
    }

    public void setGia(Long gia) {
        this.gia = gia;
    }

    public Long getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Long soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public List<String> getKichCo() {
        return kichCo;
    }

    public void setKichCo(List<String> kichCo) {
        this.kichCo = kichCo;
    }

    public String getNamSX() {
        return namSX;
    }

    public void setNamSX(String namSX) {
        this.namSX = namSX;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    HashMap<String,Object> map() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("maSp",maSp);
        map.put("anh",anh);
        map.put("tenSP",tenSP);
        map.put("gia",gia);
        map.put("soLuong",soLuong);
        map.put("maHang",maHang);
        map.put("kichCo",kichCo);
        map.put("namSX",namSX);
        map.put("time",time);
        return map;
    }
}
