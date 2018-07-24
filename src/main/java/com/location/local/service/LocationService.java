package com.location.local.service;

import com.location.local.algorithm.LbsAlgo;
import com.location.local.dao.ApDao;
import com.location.local.dao.LocationDao;
import com.location.local.model.Ap;
import com.location.local.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LocationService {
    @Resource
    LocationDao locationDao;
    @Resource
    ApDao apDao;

    public Ap selectByUsername(String username){return apDao.selectByUsername(username);}

    public void saveApInDb(double[] A,double[] B,double[] C,double[] X,double[] Y,double[] Z,String username){
        Ap ap = selectByUsername(username);

        //显示时间
        if(ap.getUsername().equals(username)){
            ap.setAlng(String.valueOf(A[0]));
            ap.setAlat(String.valueOf(A[1]));
            ap.setBlng(String.valueOf(B[0]));
            ap.setBlat(String.valueOf(B[1]));
            ap.setClng(String.valueOf(C[0]));
            ap.setClat(String.valueOf(C[1]));
            ap.setALbslng(String.valueOf(X[0]));
            ap.setALbslat(String.valueOf(X[1]));
            ap.setBLbslng(String.valueOf(Y[0]));
            ap.setBLbslat(String.valueOf(Y[1]));
            ap.setCLbslng(String.valueOf(Z[0]));
            ap.setCLbslat(String.valueOf(Z[1]));
            ap.setDate_time(new Date());
            System.out.println(ap);
            apDao.updateLocation(ap);
            System.out.println("AP更新成功");
        }else{
            ap.setUsername(username);
            ap.setAlng(String.valueOf(A[0]));
            ap.setAlat(String.valueOf(A[1]));
            ap.setBlng(String.valueOf(B[0]));
            ap.setBlat(String.valueOf(B[1]));
            ap.setClng(String.valueOf(C[0]));
            ap.setClat(String.valueOf(C[1]));
            ap.setALbslng(String.valueOf(X[0]));
            ap.setALbslat(String.valueOf(X[1]));
            ap.setBLbslng(String.valueOf(Y[0]));
            ap.setBLbslat(String.valueOf(Y[1]));
            ap.setCLbslng(String.valueOf(Z[0]));
            ap.setCLbslat(String.valueOf(Z[1]));
            ap.setDate_time(new Date());
            apDao.updateLocation(ap);
            System.out.println("AP插入成功");
        }
    }

    public void saveApInDb(double[] A,double[] B,double[] C,double[] X,double[] Y,String username){
        Ap ap = selectByUsername(username);

        //显示时间
        if(ap.getUsername().equals(username)){
            ap.setAlng(String.valueOf(A[0]));
            ap.setAlat(String.valueOf(A[1]));
            ap.setBlng(String.valueOf(B[0]));
            ap.setBlat(String.valueOf(B[1]));
            ap.setClng(String.valueOf(C[0]));
            ap.setClat(String.valueOf(C[1]));
            ap.setALbslng(String.valueOf(X[0]));
            ap.setALbslat(String.valueOf(X[1]));
            ap.setBLbslng(String.valueOf(Y[0]));
            ap.setBLbslat(String.valueOf(Y[1]));
            ap.setCLbslat("NULL");
            ap.setCLbslng("NULL");
            ap.setDate_time(new Date());
            System.out.println(ap);
            apDao.updateLocation(ap);
            System.out.println("AP更新成功");
        }else{
            ap.setUsername(username);
            ap.setAlng(String.valueOf(A[0]));
            ap.setAlat(String.valueOf(A[1]));
            ap.setBlng(String.valueOf(B[0]));
            ap.setBlat(String.valueOf(B[1]));
            ap.setClng(String.valueOf(C[0]));
            ap.setClat(String.valueOf(C[1]));
            ap.setALbslng(String.valueOf(X[0]));
            ap.setALbslat(String.valueOf(X[1]));
            ap.setBLbslng(String.valueOf(Y[0]));
            ap.setBLbslat(String.valueOf(Y[1]));
            ap.setCLbslat("NULL");
            ap.setCLbslng("NULL");
            ap.setDate_time(new Date());
            apDao.updateLocation(ap);
            System.out.println("AP插入成功");
        }
    }



}
