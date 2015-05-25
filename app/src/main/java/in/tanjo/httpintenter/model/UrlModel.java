package in.tanjo.httpintenter.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class UrlModel {

  @SerializedName("url") String url;
  @SerializedName("title") String title;

  public static String toJson(UrlModel urlModel) {
    return new Gson().toJson(urlModel, UrlModel.class);
  }

  public static UrlModel fromJson(String json) {
    return new Gson().fromJson(json, UrlModel.class);
  }

  public String toJson() {
    return toJson(this);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
