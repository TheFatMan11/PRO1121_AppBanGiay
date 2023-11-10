package com.thuydev.pro1121appbangiay.model;

public class User {
    private String maUser;
    private String Email;
    private String hoTen;
    private long SDT;
    private int trangThai;
    private Long soDu;
    private int chucVu;

    public User() {
    }

    public User(String maUser,String email, int trangThai, Long soDu, int chucVu) {
        Email = email;
        this.trangThai = trangThai;
        this.soDu = soDu;
        this.chucVu = chucVu;
        this.maUser = maUser;
    }



    public User(String maUser, String email, String hoTen, long SDT, int trangThai, Long soDu, int chucVu) {
        this.maUser = maUser;
        Email = email;
        this.hoTen = hoTen;
        this.SDT = SDT;
        this.trangThai = trangThai;
        this.soDu = soDu;
        this.chucVu = chucVu;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
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

    public long getSDT() {
        return SDT;
    }

    public void setSDT(long l) {
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

}