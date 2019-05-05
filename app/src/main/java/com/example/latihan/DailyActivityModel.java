package com.example.latihan;

public class DailyActivityModel {
    private String kegiatan;

    public DailyActivityModel(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public DailyActivityModel() {
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }
}