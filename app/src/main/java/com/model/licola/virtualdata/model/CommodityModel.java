package com.model.licola.virtualdata.model;

/**
 * Created by LiCola on 2018/3/15.
 */

public class CommodityModel {
  private String title;
  private int price;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CommodityModel{");
    sb.append("title='").append(title).append('\'');
    sb.append(", price=").append(price);
    sb.append('}');
    return sb.toString();
  }
}
