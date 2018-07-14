package com.location.local.algorithm;

public class GpsAlgo {

    public static double GpsLocation(String gps){
        int g = Integer.parseInt(gps,16);
        double l= ((double) g)/1800000;
        //System.out.println(l);
        return l;
    }
    /*
     *
     * */
    public static String gpsLatAddress(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        char a;
        for(int i=23;i<31;i++){
            a=(char)bytes[i];
            sb.append(a);
        }
        return sb.toString();
    }

    public static String gpsLonAddress(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        char a;
        for(int i=31;i<39;i++){
            a=(char)bytes[i];
            sb.append(a);
        }
        return sb.toString();
    }

    public static String getGpsTime(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        char a;
        for(int i=9;i<21;i++){
            a=(char)bytes[i];
            sb.append(a);
        }
        return sb.toString();
    }
}
