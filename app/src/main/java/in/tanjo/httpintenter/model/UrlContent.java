package in.tanjo.httpintenter.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanjo on 2014/12/03.
 */
public class UrlContent {

  public static String SAVE_SHARED_PREFERENCES = "SAVE_SHARED_PREFERENCES";
  public static String SAVE_DATA = "SAVE_DATA";
  public static String URL_CONTENT_TITLE = "title";
  public static String URL_CONTENT_URL = "url";

  public List<UrlItem> mUrlItems = new ArrayList<UrlItem>();

  public void addItem(UrlItem item) {
    mUrlItems.add(item);
  }

  public List<UrlItem> getUrlItems() {
    return mUrlItems;
  }

  public void saveUrlContent(Context context) {
    SharedPreferences sp = context.getSharedPreferences(SAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    sp.edit().putString(SAVE_DATA,this.toString()).commit();
  }

  public void restoreUrlContent(Context context) {
    SharedPreferences sp = context.getSharedPreferences(SAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    String jsonString = sp.getString(SAVE_DATA, "{\"items\":[]}");
    JSONObject json;
    try {
      json = new JSONObject(jsonString);
      JSONArray itemArray = json.getJSONArray("items");
      mUrlItems = new ArrayList<UrlItem>();
      for (int i = 0; i < itemArray.length(); i++) {
        JSONObject item = itemArray.getJSONObject(i);
        mUrlItems.add(new UrlItem(item.getString(URL_CONTENT_URL),item.getString(URL_CONTENT_TITLE)));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    String allItem = "{\"items\": [";
    for (int i = 0; i < mUrlItems.size(); i++) {
      UrlItem item = mUrlItems.get(i);
      allItem += item.toString();
      if (i != mUrlItems.size() - 1) {
        allItem += ",";
      }
    }
    allItem += "]}";
    return allItem;
  }

  public static class UrlItem {
    public String url;
    public String title;

    public UrlItem(String url, String title) {
      this.url = url;
      this.title = title;
    }

    @Override
    public String toString() {
      return "{" + "\"title\":\"" + title + "\",\"url\":\"" + url + "\"}";
    }
  }
}
