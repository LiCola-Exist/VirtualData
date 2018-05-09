package com.model.licola.virtualdata.model;

import java.util.List;

/**
 * Created by LiCola on 2018/3/15.
 * 被模拟的数据
 * 定义有基本类型，String，自定义类，List自定义类
 * 类似常用的JSON解析的VO（ViewObject）表现层对象
 */

public class CollectionUserModel {

  /**
   * 不可变的字段 不会被修改
   */
  public static final String Action = "action";

  public int times;
  public String userGitUrl;
  public String des;
  public List<UserModel> userModels;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CollectionUserModel{");
    sb.append("times=").append(times);
    sb.append(", userGitUrl='").append(userGitUrl).append('\'');
    sb.append(", des='").append(des).append('\'');
    sb.append(", userModels=").append(userModels);
    sb.append('}');
    return sb.toString();
  }
}
