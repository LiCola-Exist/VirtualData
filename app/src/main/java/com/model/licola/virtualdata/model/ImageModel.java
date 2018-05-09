package com.model.licola.virtualdata.model;

/**
 * Created by LiCola on 2018/5/9.
 */
public class ImageModel {

  public String id;
  public String hash;


  public ImageModel(String id, String hash) {
    this.id = id;
    this.hash = hash;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ImageModel{");
    sb.append("id='").append(id).append('\'');
    sb.append(", hash='").append(hash).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
