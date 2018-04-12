package com.model.licola.virtualdata.model;

import java.util.List;

/**
 * Created by LiCola on 2018/3/15.
 * 被模拟的数据
 * 定义有基本类型，String，自定义类，List自定义类
 * 类似常用的JSON解析的VO（ViewObject）表现层对象
 *
 */

public class CollectionUserModel {

  public int times;
  public String des;
  public CommodityModel commodityModel;
  public List<UserModel> userModels;



  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CollectionUserModel{");
    sb.append("userModels=").append(userModels);
    sb.append(", times=").append(times);
    sb.append(", des='").append(des).append('\'');
    sb.append(", commodityModel=").append(commodityModel);
    sb.append('}');
    return sb.toString();
  }
}
