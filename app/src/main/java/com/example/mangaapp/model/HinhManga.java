package com.example.mangaapp.model;

import java.io.Serializable;

public class HinhManga implements Serializable {
    int MaHinhChap,MaChap;
    byte[] HinhChap;

    public HinhManga() {
    }

    public HinhManga(int maHinhChap, int maChap, byte[] hinhChap) {
        MaHinhChap = maHinhChap;
        MaChap = maChap;
        HinhChap = hinhChap;
    }

    public int getMaHinhChap() {
        return MaHinhChap;
    }

    public void setMaHinhChap(int maHinhChap) {
        MaHinhChap = maHinhChap;
    }

    public int getMaChap() {
        return MaChap;
    }

    public void setMaChap(int maChap) {
        MaChap = maChap;
    }

    public byte[] getHinhChap() {
        return HinhChap;
    }

    public void setHinhChap(byte[] hinhChap) {
        HinhChap = hinhChap;
    }
}
