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
    private String ALbslng;
    private String ALbslat;
    private String BLbslng;
    private String BLbslat;
    private String CLbslng;
    private String CLbslat;
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
                ", ALbslng='" + ALbslng + '\'' +
                ", ALbslat='" + ALbslat + '\'' +
                ", BLbslng='" + BLbslng + '\'' +
                ", BLbslat='" + BLbslat + '\'' +
                ", CLbslng='" + CLbslng + '\'' +
                ", CLbslat='" + CLbslat + '\'' +
                ", created_time=" + created_time +
                '}';
    }

    public String getALbslng() {
        return ALbslng;
    }

    public void setALbslng(String ALbslng) {
        this.ALbslng = ALbslng;
    }

    public String getALbslat() {
        return ALbslat;
    }

    public void setALbslat(String ALbslat) {
        this.ALbslat = ALbslat;
    }

    public String getBLbslng() {
        return BLbslng;
    }

    public void setBLbslng(String BLbslng) {
        this.BLbslng = BLbslng;
    }

    public String getBLbslat() {
        return BLbslat;
    }

    public void setBLbslat(String BLbslat) {
        this.BLbslat = BLbslat;
    }

    public String getCLbslng() {
        return CLbslng;
    }

    public void setCLbslng(String CLbslng) {
        this.CLbslng = CLbslng;
    }

    public String getCLbslat() {
        return CLbslat;
    }

    public void setCLbslat(String CLbslat) {
        this.CLbslat = CLbslat;
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
