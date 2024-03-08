package com.example.mangaapp.model;

import java.io.Serializable;

public class MangaChap implements Serializable {
    private String chapname, ngaydang;
    private int chapid, truyenid;

    public MangaChap() {
    }

    public MangaChap(String chapname, String ngaydang, int chapid, int truyenid) {
        this.chapname = chapname;
        this.ngaydang = ngaydang;
        this.chapid = chapid;
        this.truyenid = truyenid;
    }

    public String getChapname() {
        return chapname;
    }

    public void setChapname(String chapname) {
        this.chapname = chapname;
    }

    public String getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }

    public int getChapid() {
        return chapid;
    }

    public void setChapid(int chapid) {
        this.chapid = chapid;
    }

    public int getTruyenid() {
        return truyenid;
    }

    public void setTruyenid(int truyenid) {
        this.truyenid = truyenid;
    }
}
