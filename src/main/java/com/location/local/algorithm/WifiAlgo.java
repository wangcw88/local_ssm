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
import java.util.Arrays;
public class WifiAlgo {
    public static String ap_lon=null;
    public static String ap_lat=null;
    public static long ap_errcode=0;
    //API接口查询mac的坐标
    public static void WifiLocation(String mac){
        //定义变量经纬度
        String ap_lon=null;
        String ap_lat=null;
        int ap_errcode =10001;
        String url="http://api.cellocation.com:81/wifi/?coord=gcj02&output=json&";
        String url_constant="http://api.cellocation.com:81/wifi/?coord=gcj02&output=json&";
        try {
            //创建一个HttpClient对象
            HttpClient httpclient = new DefaultHttpClient();
            url = url_constant + "mac=" + mac;
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
            //   jsonStirng!=null&&!"".equals(jsonString)
            if (json != null) {
                //System.out.println(json.toString());
                JSONObject jsonObject = new JSONObject(json);
                ap_lon = jsonObject.get("lon").toString();
                ap_lat = jsonObject.get("lat").toString();
                ap_errcode = (int) jsonObject.get("errcode");
            }
            if (ap_errcode == 10001) {
                //json = "定位失败请重新定位";
                WifiAlgo.ap_errcode=10001;
            }
            else if (ap_errcode == 10000){
                WifiAlgo.ap_errcode=10000;

            }else {
                WifiAlgo.ap_errcode=0;
                WifiAlgo.ap_lat=ap_lat;
                WifiAlgo.ap_lon=ap_lon;
            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /*
     *  三角定位算法
     *  输入 三点坐标 ：
     *  A:(x,y,r)-->( A[0], A[1] ,A[2])  B:(x,y,r)-->( B[0], B[1], B[2] )  C:(x,y,r)-->( C[0], C[1] ,C[2])
     *               经度为横坐标：x  纬度为纵坐标 ：y  信号半径：r
     *
     * */
    public static double[] locAlgorithm(double[] A, double[] B, double[] C){
        //定义P1坐标 (x,y)-->( P1[0], P1[1] )
        double[] P1= new double[2];
        //定义P2坐标 (x,y)-->( P1[0], P1[1] )
        double[] P2= new double[2];
        //定义P3坐标 (x,y)-->( P3[0], P3[1] )
        double[] P3= new double[2];
        //定义k1和k2 --->k1:K[0],k2:K[1]
        double[] K = new double[3];
        //定义b1和b2 --->b1:b[0],b2:b[1]
        double[] b = new double[3];
        //定义估算定位模块的经纬度坐标
        double[] D = new double[2];
        //定义l1和l2交点为E1(x,y)--->E1[0]:x  E1[1]:y
        double[] E1 = new double[2];
        //定义l1和l2交点为E2(x,y)--->E2[0]:x  E2[1]:y
        double[] E2 = new double[2];
        //定义l1和l2交点为E3(x,y)--->E3[0]:x  E3[1]:y
        double[] E3 = new double[2];

        double[] A1 = new double[2];
        double[] B1 = new double[2];
        double[] C1 = new double[2];
        A1[0]=A[0];
        A1[1]=A[1];
        B1[0]=B[0];
        B1[1]=B[1];
        C1[0]=C[0];
        C1[1]=C[1];
        // 三点坐标均不相同的情况

        if(Arrays.equals(B1,C1)) {
            System.out.println(A1[0] + "  " + B1[0] + " " + C1[0]);
        }
        if(!Arrays.equals(A1,B1) && !Arrays.equals(A1,C1) && !Arrays.equals(B1,C1)) {

            //确定P1横坐标---> X=((r1/r2)*X2+X1)*(r2/(r1+r2))
            P1[0] = (A[0] + B[0] * (A[2] / B[2])) * (B[2] / (A[2] + B[2]));
            //确定P1纵坐标---> Y=((r1/r2)*Y2+Y1)*(r2/(r1+r2))
            P1[1] = (A[1] + B[1] * (A[2] / B[2])) * (B[2] / (A[2] + B[2]));

            //确定P2横坐标---> X=((r2/r3)*X3+X2)*(r3/(r3+r2))
            P2[0] = (B[0] + C[0] * (B[2] / C[2])) * (C[2] / (C[2] + B[2]));
            //确定P2纵坐标---> Y=((r2/r3)*Y3+Y2)*(r3/(r3+r2))
            P2[1] = (B[1] + C[1] * (B[2] / C[2])) * (C[2] / (C[2] + B[2]));

            //确定P3横坐标---> X=((r1/r3)*X3+X1)*(r3/(r3+r1))
            P3[0] = (A[0] + C[0] * (A[2] / C[2])) * (C[2] / (C[2] + A[2]));
            //确定P3纵坐标---> Y=((r1/r3)*Y3+Y1)*(r3/(r3+r1))
            P3[1] = (A[1] + C[1] * (A[2] / C[2])) * (C[2] / (C[2] + A[2]));


            //确定过P1点且与直线AB垂直的直线l1：Y=k1*X+b1
            K[0] = (A[0]-B[0])/(B[1]-A[1]);
            b[0] = P1[1]-P1[0]*K[0];

            //确定过P2点和直线BC的直线l2：Y=k2*X+b2
            K[1] = (B[0]-C[0])/(C[1]-B[1]);
            b[1] = P2[1]-P2[0]*K[1];

            //确定过P3点和直线AC的直线l3：Y=k3*X+b3
            K[2] = (A[0]-C[0])/(C[1]-A[1]);
            b[2] = P3[1]-P3[0]*K[2];


            //求l1和l2相交点E1的坐标（X,Y）
            if(K[0]!=K[1] || b[0]!=b[1]) {
                E1[0] = (b[1] - b[0]) / (K[0] - K[1]);
                E1[1] = b[0] + K[0] * E1[0];
            }

            //求l2和l3相交点E2的坐标（X,Y）
            if(K[2]!=K[1] || b[2]!=b[1]) {
                E2[0] = (b[2] - b[1]) / (K[1] - K[2]);
                E2[1] = b[1] + K[1] * E2[0];
            }


            //求l1和l3相交点E3的坐标（X,Y）
            if(K[2]!=K[0] || b[2]!=b[0]) {
                E3[0] = (b[2] - b[0]) / (K[0] - K[2]);
                E3[1] = b[0] + K[0] * E3[0];
            }

            /*******求这三点的重心********/
            D = WifiAlgo.gravityAlg(E1,E2,E3);

        }
        // 三点坐标有相同的情况下 A和B相同重心为P3或P2
        if(Arrays.equals(A1,B1) && !Arrays.equals(B1,C1)){
            //重心为P3
            //System.out.println("A和B相同");
            if(A[2]<=B[2]) {

                D[0] = (A[0] + C[0] * (A[2] / C[2])) * (C[2] / (C[2] + A[2]));
                D[1] = (A[1] + C[1] * (A[2] / C[2])) * (C[2] / (C[2] + A[2]));

            }else{ //重心为P2

                D[0] = (B[0] + C[0] * (B[2] / C[2])) * (C[2] / (C[2] + B[2]));
                D[1] = (B[1] + C[1] * (B[2] / C[2])) * (C[2] / (C[2] + B[2]));

            }
        }

        //B和C相同重心为P1或P3
        if(Arrays.equals(B1,C1) && !Arrays.equals(A1,B1)){
            //重心为P1
            //System.out.println("B和C相同");
            if(B[2]<=C[2]){

                D[0] = (A[0] + B[0] * (A[2] / B[2])) * (B[2] / (A[2] + B[2]));
                D[1] = (A[1] + B[1] * (A[2] / B[2])) * (B[2] / (A[2] + B[2]));

            }else{ //重心为P3
                D[0] = (C[0] * (A[2] / C[2])+A[0]) * (C[2] / (C[2] + A[2]));
                D[1] = (A[1] + C[1] * (A[2] / C[2])) * (C[2] / (C[2] + A[2]));
            }
        }
        if(Arrays.equals(A1,C1) && !Arrays.equals(A1,B1) ){
            //重心为P1

            //System.out.println("A和C相同");
            if(A[2]<=C[2]){

                D[0] = (A1[0] + B1[0] * (A[2] / B[2])) * (B[2] / (A[2] + B[2]));
                D[1] = (A1[1] + B1[1] * (A[2] / B[2])) * (B[2] / (A[2] + B[2]));
            }
            else{//重心为P2
                D[0] = (B1[0]+C1[0] * (B[2] / C[2])) * (C[2] / (C[2] + B[2]));
                D[1] = (B1[1] + C1[1] * (B[2] / C[2])) * (C[2] / (C[2] + B[2]));
            }

        }

        System.out.println(D[0]+"  "+ D[1]);
        return D;
    }

    /*
     *  求三角形重心算法
     *  输入 三点坐标 ：
     *  A:(x,y)-->( A[0], A[1] )  B:(x,y,r)-->( B[0], B[1] )  C:(x,y,r)-->( C[0], C[1] )
     *               经度为横坐标：x  纬度为纵坐标 ：y
     *  输出 重心坐标 ： gravity:(x,y)
     *  类型： double
     * */
    public static double[] gravityAlg(double[] A, double[] B, double[] C){
        double[] G= new double[2];
        G[0]=(A[0]+B[0]+C[0])/3;
        G[1]=(A[1]+B[1]+C[1])/3;
        return G;
    }

}
