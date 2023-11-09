package com.thuydev.pro1121appbangiay.model;

public class Hang {
    private String maHang;
    private String tenHang;
    private Long time;

    public Hang() {
    }

    public Hang(String maHang, String tenHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
    }

    public Hang(String maHang, String tenHang, Long time) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.time = time;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }
}
