package com.location.local.model;


public class User {

    private int id;
    private String username;
    private String password;
    private String lng;
    private String lat;
    private String gps;
    private String wifi;
    private String lbs;

    public User(){ }

    public String getLbs() { return lbs; }

    public void setLbs(String lbs) { this.lbs = lbs; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getGps() { return gps; }

    public void setWifi(String wifi) { this.wifi = wifi;}

    public String getWifi() { return wifi; }


}
