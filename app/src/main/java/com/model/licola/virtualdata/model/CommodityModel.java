package com.model.licola.virtualdata.model;

/**
 * Created by LiCola on 2018/3/15.
 */

public class CommodityModel {
  private String title;
  private int price;
  private int level;

  public CommodityModel() {
  }

  public CommodityModel(String title, int price, int level) {
    this.title = title;
    this.price = price;
    this.level = level;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CommodityModel{");
    sb.append("title='").append(title).append('\'');
    sb.append(", price=").append(price);
    sb.append(", level=").append(level);
    sb.append('}');
    return sb.toString();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }
}
