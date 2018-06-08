package com.model.licola.virtualdata.model;

/**
 * Created by LiCola on 2018/3/15.
 */

public class UserModel {

  private String userId;
  private String username;
  private String phone;
  private int age;
  private float grade;
  private String gitUrl;

  public UserModel() {
  }

  public UserModel(String userId, String username, String phone, int age, float grade,
      String gitUrl) {
    this.userId = userId;
    this.username = username;
    this.phone = phone;
    this.age = age;
    this.grade = grade;
    this.gitUrl = gitUrl;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserModel{");
    sb.append("userId='").append(userId).append('\'');
    sb.append(", username='").append(username).append('\'');
    sb.append(", phone='").append(phone).append('\'');
    sb.append(", age=").append(age);
    sb.append(", grade=").append(grade);
    sb.append(", gitUrl='").append(gitUrl).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
