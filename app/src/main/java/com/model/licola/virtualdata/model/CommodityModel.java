package com.model.licola.virtualdata.model;

import java.util.Map;

/**
 * Created by LiCola on 2018/3/15.
 */

public class CommodityModel {

  private String title;
  private int price;
  private int level;
  private String phoneNumber;
  private Map<String, Integer> userMap;
  private ImageModel imageModel;

  public CommodityModel() {
  }

  public CommodityModel(String title, int price, int level) {
    this.title = title;
    this.price = price;
    this.level = level;
  }

  public Map<String, Integer> getUserMap() {
    return userMap;
  }

  public void setUserMap(Map<String, Integer> userMap) {
    this.userMap = userMap;
  }

  public ImageModel getImageModel() {
    return imageModel;
  }

  public void setImageModel(ImageModel imageModel) {
    this.imageModel = imageModel;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CommodityModel{");
    sb.append("title='").append(title).append('\'');
    sb.append(", price=").append(price);
    sb.append(", level=").append(level);
    sb.append(", phoneNumber='").append(phoneNumber).append('\'');
    sb.append(", userMap=").append(userMap);
    sb.append(", imageModel=").append(imageModel);
    sb.append('}');
    return sb.toString();
  }
}
