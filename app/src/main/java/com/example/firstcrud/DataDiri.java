package com.example.firstcrud;

import androidx.annotation.NonNull;

public class DataDiri {
    private String id;
    private String nama;
    private String alamat;

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public DataDiri(String id, String nama, String alamat) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
    }

    public DataDiri(){

    }

    @NonNull
    @Override
    public String toString() {
        return this.nama;
    }
}
