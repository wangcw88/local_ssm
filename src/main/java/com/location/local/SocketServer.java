package com.location.local;

import com.location.local.algorithm.GpsAlgo;
import com.location.local.algorithm.WifiAlgo;
import com.location.local.SocketServer;
import com.location.local.dao.UserDao;
import com.location.local.model.User;
import com.location.local.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.*;
import static java.lang.Double.valueOf;

//@ComponentScan("com.location.local")
public class SocketServer implements Runnable {

    public static String wifi_lat=null;
    public static String wifi_lon=null;
//    ApplicationContext appCtx = SpringTool.getApplicationContext();
//    StatusMapper statusMapper = (StatusMapper)appCtx.getBean(StatusMapper.class);

    public Socket client = null;
    String imei;

    public SocketServer(Socket client) {
        this.client = client;
    }

//    public void setClient(Socket client) {
//        this.client = client;
//    }

    //字符转16进制
    public static String bytesToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        for(int i = 0; i < b.length; i++){
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }

    @Override
    public void run() {
        try {
            //获取输入流 并读取客户端信息
            InputStream is = client.getInputStream();

//            //获取输出流
//            OutputStream os = client.getOutputStream();

            //获取Socket端输出流，用来向客户端发送数据
            //PrintStream out = new PrintStream(os);

            int i = 0;
            byte[] by = new byte[1024];
            byte[] byHex;

            //开始读取设备发送到数据
            while ((i = is.read(by)) != -1) {
                //打印设备发送至服务器到数据 16进制接收显示
                System.out.println(bytesToHexString(by));

                //获取系统时间
                Date now = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                //打印时间至控制台
                System.out.println(simpleDateFormat.format(now));

                int x = 2 * i;
                byHex = bytesToHexString(by).getBytes();

                //向设备发送
                replyToDevice(byHex, x);
//                os.write(replyToDevice(byHex, x));
//                os.flush();

                //打印服务器回复设备的数据 16进制数显示发送
                //System.out.println(bytesToHexString(replyToDevice(byHex, x)));
                //System.out.println();
            }
            //关闭socket输入
            client.shutdownInput();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果socket无法连接 就关闭socket连接
            if (client == null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //对设备发送对数据进行回复
    public void replyToDevice(byte[] bytes, int i) {
        //登陆回复
        byte[] b_login = new byte[]{0x78, 0x78, 0x01, 0x01, 0x0D, 0x0A};

        //进行同步数据回复
        byte[] b_state = new byte[]{0x78, 0x78, 0x1F, 0x57, 0x00, 0x30, 0x01, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0d, 0x0a};

        byte[] b_synchronize = new byte[]{0x78, 0x78, 0x1F, 0x57, 0x00, 0x60, 0x01, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x3B, 0x3B, 0x3B, 0x0d, 0x0a};

        //心跳包回复
        byte[] b_heart = new byte[]{0x78, 0x78, 0x01, (byte) 0x80, 0x0d, 0x0a};

        byte[] b_st=new byte[]{0x78,0x78,0x01,0x13,0x0d,0x0a};

        byte[] b_u =new byte[]{0x78,0x78, 0x03, (byte) 0x97 ,0x00,0x0E, 0x0D,0x0A};

        try {
            //获取输出流
            OutputStream os = client.getOutputStream();
            //判断头是7878 尾是0d0a
            if (bytes[0] == 55 && bytes[1] == 56 && bytes[2] == 55 && bytes[3] == 56
                    && bytes[i - 4] == 48 && bytes[i - 3] == 100 && bytes[i - 2] == 48 && bytes[i - 1] == 97) {

                //登入协议0x01
                if (bytes[6] == 48 && bytes[7] == 49) {
                    // return b_login;
                    imei=getImei(bytes);
                    os.write(b_login);
                    os.flush();
                    System.out.println(bytesToHexString(b_login));
                    System.out.println();
                }
                //心跳包协议0x08
                if(bytes[6]==48 && bytes[7]==56){
                    os.write(b_heart);
                    os.flush();
                    System.out.println(bytesToHexString(b_heart));
                    System.out.println();
                }

                //Gps数据协议0x10
                if(bytes[6] == 49 && bytes[7] == 48){
                    //D[0]为lat,D[1]为lon
                    double[] L = new double[2];
                    //进行Gps数据解析
                    L[0]=GpsAlgo.GpsLocation(GpsAlgo.gpsLatAddress(bytes));
                    L[1]=GpsAlgo.GpsLocation(GpsAlgo.gpsLonAddress(bytes));

                    //UserService.saveInSQL(L);
                    //return strToByteArray(GpsServer.getGpsTime(bytes));

                }
                if(bytes[6]==56 && bytes[7]==48){

                }

                //wifi数据协议0x69
                if (bytes[6] == 54 && bytes[7] == 57){

                    //定义变量表示采集到的wifi数量
                    int count = bytes[5]-48;
                    boolean a;
                    boolean b;
                    boolean c;
                    String[][] array;
                    double[] A = new double[3];
                    double[] B = new double[3];
                    double[] C = new double[3];
                    double[] D = new double[2];

                    //将每个热点的mac地址和对应的信号rssi存放进二维数组中
                    array = macrssi(count, bytes);
                    //根据rssi的大小对bassid由近到远进行排序
                    rssiSort(array, count);
                    //对之前排序后对二维数组进行删除过滤
                    array=sortToMac(array,count);

                    //将地址以double的形式进行存储，取强度最大的前三个AP
                    a = wifiMsg(array, 1, A, false);
                    b = wifiMsg(array, 2, B, false);
                    c = wifiMsg(array, 3, C, false);

                    //进行三角定位 并插入或者更新数据库
                    if (a & b & c) {
                        D = WifiAlgo.locAlgorithm(A, B, C);
                        wifi_lat = String.valueOf(D[1]);
                        wifi_lon = String.valueOf(D[0]);
                        new UserService().saveInSQL(D);

                    }


                    //更新设备位置信息
                    if(bytes[6] == 54 && bytes[7] == 57){
                        // return timeConvert_69();
                        os.write(timeConvert_69());
                        os.flush();
                        System.out.println(bytesToHexString(timeConvert_69()));
                        System.out.println();

                    }
                    else if((bytes[6] == 56 && bytes[7] == 48 && bytes[5] == 50 && bytes[4]==48)){
                        //return b_heart;
                        //os.write(b_st);
                        //os.flush();
                        //System.out.println(bytesToHexString(b_st));
                        System.out.println();

                    }
                    else {
                        //return timeConvert_17();
                        os.write(timeConvert_17());
                        os.flush();
                        System.out.println(bytesToHexString(timeConvert_17()));
                        System.out.println();
                    }
                }

                //离线wifi数据协议0x17


                //同步设置数据协议0x57
                if (bytes[6] == 53 && bytes[7] == 55) {
                    //return b_synchronize;
                    os.write(b_synchronize);
                    os.flush();
                    System.out.println(bytesToHexString(b_synchronize));
                    System.out.println();
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //0x69或0x17协议下回复系统时间  例如2018/5/10 20:12:23 转换为 0x18 0x05 0x10 0x20 0x12 0x23
    private byte[] timeConvert_69(){
        byte[] head={0x78,0x78,0x06,0x69};
        byte[] tail={0x0d,0x0a};
        byte[] time;
        String s;
        //获取系统时间
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        s=simpleDateFormat.format(now).toString();
        time = addBytes(head,change(s));
        time =addBytes(time,tail);
        return time;
    }

    private byte[] timeConvert_17(){
        byte[] head={0x78,0x78,0x06,0x17};
        byte[] tail={0x0d,0x0a};
        byte[] time;
        String s;
        //获取系统时间
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        s=simpleDateFormat.format(now).toString();
        time = addBytes(head,change(s));
        time =addBytes(time,tail);
        return time;
    }

    //字符串转16进制字符数组输出
    private byte[] change(String inputStr) {
        byte[] result = new byte[inputStr.length() / 2];
        for (int i = 0; i < inputStr.length() / 2; ++i)
            result[i] = (byte)(Integer.parseInt(inputStr.substring(i * 2, i * 2 +2), 16) & 0xff);
        return result;
    }

    //byte数组合并
    private byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }

    //对协议号为0x30 更新请求下 回复系统时间 例如2018/5/10 20:12:23 转换为 0x07 0xE2 0x05 0x10 0x14 0x0c 0x17
    public byte[] updateTime(){
        return null;
    }

    //提取wifi数据包中热点的mac地址和信号强度rssi
    /*
     * @param count 为Wi-Fi数量
     *          numth表示需要返回第几个地址
     * */
    private static String macAddress(int num,byte[] bytes){
        StringBuilder sb = new StringBuilder();
        char a;

        for(int i =0;i<12;i++){
            a=(char)bytes[20+14*(num-1)+i];
            if((i+20+14*(num-1))%2 == 0 && i !=0){
                //添加"："
                sb.append((char) 58);
            }
            sb.append(a);
        }
        return sb.toString();
    }

    /*
     *
     * 功能  获取各个wifi的信号强度
     * @param count 为wifi数量
     *        num 为返回第几个wifi的强度
     * */
    private static String rssi_nth(int num,byte[] bytes){
        StringBuilder sb = new StringBuilder();
        char a;
        for(int i=0;i<2;i++){
            a=(char)bytes[32+i+(num-1)*14];
            sb.append(a);
        }
        return sb.toString();
    }

    /**
     * 功能 根据信号强度对rssi值进行冒泡排序---强度由大至小
     * @param strings 关于信号强度的字符串数组strings，
     * @param count
     * */
    private static void rssiSort(String[][] strings,int count){
        int x,y;
        //int len = strings.length;
        String tmp_mac;
        String tmp_rssi;
        for(x=0;x<count-1;++x){
            for(y=count-1;y>x;--y){
                if(strings[0][y].compareTo(strings[0][y-1])<=0){
                    tmp_rssi = strings[0][y];
                    strings[0][y]=strings[0][y-1];
                    strings[0][y-1]=tmp_rssi;

                    tmp_mac = strings[1][y];
                    strings[1][y]=strings[1][y-1];
                    strings[1][y-1]=tmp_mac;
                }
            }
        }
    }

    /*
     *   将nac地址和对应的rssi存放在二维数组里，用来给其他方法调用
     * */
    private static String[][] macrssi(int count,byte[] bytes){
        String[][] array= new String[2][count];
        for(int i=0;i<count;i++){
            array[1][i]=macAddress(i+1,bytes);
            array[0][i]=rssi_nth(i+1,bytes);
        }
        return array;
    }


    /*
     *  将wifi信号强度rssi转换为距离 单位为m
     *
     * */
    private static double rssiValue(String strings){
        double i = Integer.parseInt(strings, 16);
        double distance= Math.pow(10,(i-40)/25);
        return distance;
    }

    /*
     *    打印显示 mac  对应到经纬度 以及定位器到AP的估计距离
     *    @param 存放mac和rssi的二维数组：arrays  一维数组：array  标识位：log 第几个mac :i
     * */

    private static boolean wifiMsg(String[][] arrays, int i, double[] array, boolean log){
        WifiAlgo.WifiLocation(arrays[1][i-1]);
        //System.out.println(arrays[1][i-1]+"  "+arrays[0][i-1]);
        if(WifiAlgo.ap_errcode != 10001) {
            array[0] = valueOf(WifiAlgo.ap_lon.toString());
            array[1] = valueOf(WifiAlgo.ap_lat.toString());
            array[2] = rssiValue(arrays[0][i-1]);
            System.out.println(array[0]+"  "+array[1]+"  "+array[2]);
            log=true;
        }
        else {
            System.out.println("mac地址没有对应的坐标");
        }
        return log;
    }
    /**
     *
     *  @function  筛选有有效地理位置的mac地址 并对其进行排序并删减
     *  @param    arrays 存放mac和rssi的二维数组
     *  @param    num    收集到wifi热点的数量
     * */
    private static String[][] sortToMac(String[][] arrays,int num){
        String[][] newArrays=new String[2][8];
        //count表示取有效mac点
        int count=0;
        for(int i = 0;i<num;i++){
            //通过http请求判断mac所对应对地理位置是否可查询
            WifiAlgo.WifiLocation(arrays[1][i]);
            if(WifiAlgo.ap_errcode != 10001){
                //对二维数组进行重新排序
                newArrays[0][count]=arrays[0][i];
                newArrays[1][count]=arrays[1][i];
                count++;
            }
            if(count==3){
                break;
            }
        }
        return newArrays;
    }

//    @Autowired
//    static UserDao userDao;
//    private void saveInSQL(double[] D){
//
//        User user=new User();
//        user.setUsername("wang");
////        user=userDao.selectByUsername("wangcw");
//        if(user.getUsername().equals("wang")){
////            user.setLng("111");
////            user.setLat("222");
//            user.setLng(String.valueOf(D[0]));
//            user.setLat(String.valueOf(D[1]));
//
//            System.out.println(user);
//            //userDao.updateLocation(user);
//            System.out.println("更新成功");
//        }else{
//            user.setUsername("wang");
//            user.setLng(String.valueOf(D[0]));
//            user.setLat(String.valueOf(D[1]));
//            userDao.updateLocation(user);
//            System.out.println("插入成功");
//        }

//        if (userDaoImp.Findusername("wangcw")) {
//            //更新数据库
//            userDaoImp.update(String.valueOf(D[1]), String.valueOf(D[0]), "wangcw");
//            System.out.println("更新成功");
//        } else {
//            //创建插入数据库
//            userDaoImp.create(String.valueOf(D[1]), String.valueOf(D[0]), "wangcw");
//            System.out.println("插入成功");
//        }
//    }

    private static String getImei(byte[] bytes){
        StringBuilder imei =new StringBuilder();
        char a;
        for(int i=0;i<16;i++){
            a = (char)bytes[7+i];
            imei.append(a);
        }
        return imei.toString();
    }

    /**
     * 取imei后6位作为id
     */
    private static String getLast6ID(String str){
        return str.substring(str.length()-6);
    }

}


