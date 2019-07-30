package com.mug.pesona_sukabumi;

public class GalleryActivityModel {
    String penunjang, poto;

    public GalleryActivityModel() {
    }

    public String getPoto() {
        return poto;
    }

    public void setPoto(String poto) {
        this.poto = poto;
    }

    public String getPenunjang() {
        return penunjang;
    }

    public void setPenunjang(String penunjang) {
        this.penunjang = penunjang;
    }

    @Override
    public String toString() {
        return  " " + penunjang + "\n" +
                " " + poto;
    }
}
