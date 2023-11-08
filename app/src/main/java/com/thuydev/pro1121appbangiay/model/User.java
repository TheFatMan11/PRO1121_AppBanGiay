package com.thuydev.pro1121appbangiay.model;

import java.util.HashMap;

public class User {
    private String Email;
    private String hoTen;
    private String SDT;
    private int trangThai;
    private Long soDu;
    private int chucVu;

    public User() {
    }

    public User(String email, int trangThai, Long soDu, int chucVu) {
        Email = email;
        this.trangThai = trangThai;
        this.soDu = soDu;
        this.chucVu = chucVu;
    }

    public User(String email, String hoTen, String SDT, int trangThai, Long soDu, int chucVu) {
        Email = email;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.trangThai = trangThai;
        this.soDu = soDu;
        this.chucVu = chucVu;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Long getSoDu() {
        return soDu;
    }

    public void setSoDu(Long soDu) {
        this.soDu = soDu;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }

    HashMap<String, Object> mapDangKy(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("email",getEmail());
        map.put("trangThai",getTrangThai());
        map.put("chucVu",getChucVu());
        map.put("soDu",getSoDu());
        return map;
    }
    HashMap<String, Object> map(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("email",getEmail());
        map.put("hoTen",getHoTen());
        map.put("sdt",getSDT());
        map.put("trangThai",getTrangThai());
        map.put("chucVu",getChucVu());
        map.put("soDu",getSoDu());
        return map;
    }
}
