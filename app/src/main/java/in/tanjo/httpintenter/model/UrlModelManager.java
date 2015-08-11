package in.tanjo.httpintenter.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UrlModelManager {
  public static String SAVE_SHARED_PREFERENCES = "SAVE_SHARED_PREFERENCES";
  public static String SAVE_DATA = "SAVE_DATA";

  private Context mContext;
  private UrlModels mUrlModels;

  public UrlModelManager(Context context) {
    mContext = context;
  }

  public void save() {
    SharedPreferences sp = mContext.getSharedPreferences(SAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    sp.edit().putString(SAVE_DATA, mUrlModels.toJson()).apply();
  }

  public void restore() {
    SharedPreferences sp = mContext.getSharedPreferences(SAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    String json = sp.getString(SAVE_DATA, "{\"items\":[]}");
    mUrlModels = UrlModels.fromJson(json);
  }

  public void delete() {
    SharedPreferences sp = mContext.getSharedPreferences(SAVE_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    sp.edit().clear().apply();
  }


  @Override
  public String toString() {
    return mUrlModels.toJson();
  }

  public UrlModels getUrlModels() {
    return mUrlModels;
  }

  public void setUrlModels(UrlModels urlModels) {
    mUrlModels = urlModels;
  }

  public Context getContext() {
    return mContext;
  }

  public void setContext(Context context) {
    mContext = context;
  }
}
