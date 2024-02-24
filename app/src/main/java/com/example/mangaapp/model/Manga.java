package com.example.mangaapp.model;

import java.io.Serializable;

public class Manga implements Serializable {
    private String tenchap, tentruyen;
    private int imgtruyen;

    public Manga(){

    }
    public Manga(String tenchap, String tentruyen, int imgtruyen) {
        this.tenchap = tenchap;
        this.tentruyen = tentruyen;
        this.imgtruyen = imgtruyen;
    }

    public String getTenchap() {
        return tenchap;
    }

    public void setTenchap(String tenchap) {
        this.tenchap = tenchap;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public int getImgtruyen() {
        return imgtruyen;
    }

    public void setImgtruyen(int imgtruyen) {
        this.imgtruyen = imgtruyen;
    }
}
