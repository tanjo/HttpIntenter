package in.tanjo.httpintenter.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UrlModels {

  @SerializedName("items")
  List<UrlModel> mItems;

  public static String toJson(UrlModels urlModels) {
    return new Gson().toJson(urlModels, UrlModels.class);
  }

  public static UrlModels fromJson(String json) {
    return new Gson().fromJson(json, UrlModels.class);
  }

  public String toJson() {
    return toJson(this);
  }

  public List<UrlModel> getItems() {
    return mItems;
  }

  public void setItems(List<UrlModel> items) {
    mItems = items;
  }
}
