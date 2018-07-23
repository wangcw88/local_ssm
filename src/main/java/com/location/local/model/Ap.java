package com.location.local.model;

import java.util.Date;

public class Ap {

    private int id;
    private String username;
    private String Alng;
    private String Alat;
    private String Blng;
    private String Blat;
    private String Clng;
    private String Clat;
    private String Lbslng;
    private String Lbslat;
    private Date created_time;

    public Ap(){ }

    @Override
    public String toString() {
        return "Ap{" +
                "username='" + username + '\'' +
                ", Alng='" + Alng + '\'' +
                ", Alat='" + Alat + '\'' +
                ", Blng='" + Blng + '\'' +
                ", Blat='" + Blat + '\'' +
                ", Clng='" + Clng + '\'' +
                ", Clat='" + Clat + '\'' +
                ", Lbslng='" + Lbslng + '\'' +
                ", Lbslat='" + Lbslat + '\'' +
                ", created_time=" + created_time +
                '}';
    }

    public String getLbslng() {
        return Lbslng;
    }

    public void setLbslng(String lbslng) {
        Lbslng = lbslng;
    }

    public String getLbslat() {
        return Lbslat;
    }

    public void setLbslat(String lbslat) {
        Lbslat = lbslat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAlng() {
        return Alng;
    }

    public void setAlng(String alng) {
        Alng = alng;
    }

    public String getAlat() {
        return Alat;
    }

    public void setAlat(String alat) {
        Alat = alat;
    }

    public String getBlng() {
        return Blng;
    }

    public void setBlng(String blng) {
        Blng = blng;
    }

    public String getBlat() {
        return Blat;
    }

    public void setBlat(String blat) {
        Blat = blat;
    }

    public String getClng() {
        return Clng;
    }

    public void setClng(String clng) {
        Clng = clng;
    }

    public String getClat() {
        return Clat;
    }

    public void setClat(String clat) {
        Clat = clat;
    }

    public Date getDate_time() { return created_time; }

    public void setDate_time(Date date_time) { this.created_time = date_time; }
}
