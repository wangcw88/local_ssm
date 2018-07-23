package com.location.local.algorithm;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LbsAlgo {
    public static String lbs_lon=null;
    public static String lbs_lat=null;
    public static long lbs_errcode=0;

    //提取wifi/lbs数据包中基站的地址--lac
    /**
     * @param counts 为Wi-Fi数量
     * @param bytes  表示得到到字符串
     * @return cellid 返回lbs地址
     * */
    public static String lbsToLac(int counts,byte[] bytes){
        StringBuilder lac = new StringBuilder();
        char a;
        //wifi数量
        int wifi_count = bytes[5]-48;
        //基站数量
        int lbs_count=bytes[21+14*wifi_count]-48;

        if(counts>lbs_count){
            return null;
        }
        for(int i=0;i<4;i++){
            a=(char)bytes[22+wifi_count*14+i+counts*10];
            lac.append(a);
        }

        return String.valueOf(Integer.valueOf(lac.toString(),16));
    }
    //提取wifi/lbs数据包中基站的地址--cellid
    /**
     * @param counts 为Wi-Fi数量
     * @param bytes  表示得到到字符串
     * @return cellid 返回lbs地址
     * */
    public static String lbsToCi(int counts,byte[] bytes){
        StringBuilder ci = new StringBuilder();
        char a;
        //wifi数量
        int wifi_count = bytes[5]-48;
        //基站数量
        int lbs_count=bytes[21+14*wifi_count]-48;

        if(counts>lbs_count){
            return null;
        }
        for(int i=4;i<8;i++){
            a=(char)bytes[22+wifi_count*14+i+counts*10];
            ci.append(a);
        }

        return String.valueOf(Integer.valueOf(ci.toString(),16));
    }

    /**
     * @param counts 表示想获取第几个基站的数据
     * @param bytes  表示lbs信息的字符串
     */
    public static int lacToMciss(int counts,byte[] bytes){
        StringBuilder mciss = new StringBuilder();
        char a;
        //wifi数量
        int wifi_count = bytes[5]-48;
        //基站数量
        int lbs_count=bytes[21+14*wifi_count]-48;

        if(counts>lbs_count){
            return 0;
        }
        for(int i=8;i<10;i++){
            a=(char)bytes[22+wifi_count*14+i+counts*10];
            mciss.append(a);
        }

        return Integer.valueOf(mciss.toString(),16);
    }


    /**
     * @param lac lbs的lac
     * @param ci  lbs的cellid
     * */
    public static void LBSLocation(String lac ,String ci){
        //定义变量经纬度
        String lbs_lon=null;
        String lbs_lat=null;

        int lbs_errcode =10001;
        String url="http://api.cellocation.com:81/cell/?coord=gcj02&output=json&";
        String url_constant="http://api.cellocation.com:81/cell/?coord=gcj02&output=json&mcc=460&mnc=0&";
        try {
            //创建一个HttpClient对象
            HttpClient httpclient = new DefaultHttpClient();
            url = url_constant + "lac=" + lac + "&ci=" + ci;
            //Log.d("远程URL", url);
            //创建HttpGet对象
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "text/json");
            //获取响应的结果
            HttpResponse response = httpclient.execute(request);
            //获取HttpEntity
            HttpEntity entity = response.getEntity();
            //获取响应的结果信息
            String json = EntityUtils.toString(entity, "UTF-8");

            if (json != null) {
                JSONObject jsonObject = new JSONObject(json);
                lbs_lon = jsonObject.get("lon").toString();
                lbs_lat = jsonObject.get("lat").toString();
                lbs_errcode = (int) jsonObject.get("errcode");
            }
            if (lbs_errcode == 10001) {
                //json = "定位失败请重新定位";
                LbsAlgo.lbs_errcode=10001;
            }
            else if (lbs_errcode == 10000){
                LbsAlgo.lbs_errcode=10000;

            }else {
                LbsAlgo.lbs_errcode=0;
                LbsAlgo.lbs_lat=lbs_lat;
                LbsAlgo.lbs_lon=lbs_lon;
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }


    }
}
