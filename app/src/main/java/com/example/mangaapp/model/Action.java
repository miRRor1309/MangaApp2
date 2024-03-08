package com.example.mangaapp.model;

import java.io.Serializable;

public class Action implements Serializable {
    private int HinhAc;
    private  String TentruyenAc;
    private  int ChapAc;

    public Action() {
    }

    public Action(int hinh, String tentruyen, int chap) {
        HinhAc = hinh;
        TentruyenAc = tentruyen;
        ChapAc = chap;
    }

    public int getChapAc() {
        return ChapAc;
    }

    public void setChapAc(int chapAc) {
        ChapAc = chapAc;
    }

    public int getHinhAc() {
        return HinhAc;
    }

    public void setHinh(int hinhAc) {
        HinhAc = hinhAc;
    }

    public String getTentruyenAc() {
        return TentruyenAc;
    }

    public void setTentruyenAc(String tentruyenAc) {
        this.TentruyenAc = tentruyenAc;
    }
}
