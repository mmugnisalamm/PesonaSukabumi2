package com.mug.pesona_sukabumi;

public class TempatPariwisataModel {
    private String nama, gambar, deskripsi, latitude, longitude;

    public TempatPariwisataModel(String nama, String gambar, String deskripsi, String latitude, String longitude) {
        this.nama = nama;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public TempatPariwisataModel() {
    }

    public String getNama() {
        return nama;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return  " " + deskripsi + "\n" +
                " " + gambar + "\n" +
                " " + latitude + "\n" +
                " " + longitude + "\n" +
                " " + nama;
    }

}