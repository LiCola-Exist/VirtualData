package com.model.licola.virtualdata.model;

/**
 * Created by LiCola on 2018/3/15.
 */

public class UserModel {
  public String username;
  public String phone;
  public int age;
  public float grade;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserModel{");
    sb.append("username='").append(username).append('\'');
    sb.append(", phone='").append(phone).append('\'');
    sb.append(", age=").append(age);
    sb.append(", grade=").append(grade);
    sb.append('}');
    return sb.toString();
  }
}
