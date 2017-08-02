package in.tanjo.httpintenter.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

import in.tanjo.httpintenter.BuildConfig;
import in.tanjo.httpintenter.util.StringUtils;

public class SharedPreferenceManager {

  private static final int INT_DEFAULT = -1;

  private static final String STRING_DEFAULT = "";

  private static final boolean BOOLEAN_DEFAULT = false;

  @NonNull
  private SharedPreferences sharedPreferences;

  public SharedPreferenceManager(@NonNull Context context, @NonNull String filename) {
    this.sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID + "." + filename, Context.MODE_PRIVATE);
  }

  protected void deleteAll() {
    sharedPreferences.edit().clear().apply();
  }

  void delete(@NonNull String key) {
    sharedPreferences.edit().remove(key).apply();
  }

  void saveString(@NonNull String key, @Nullable String value) {
    sharedPreferences.edit().putString(key, value).apply();
  }

  @NonNull
  String getString(@NonNull String key) {
    return StringUtils.nullToEmpty(sharedPreferences.getString(key, STRING_DEFAULT));
  }

  protected void saveBoolean(@NonNull String key, boolean value) {
    sharedPreferences.edit().putBoolean(key, value).apply();
  }

  protected boolean getBoolean(@NonNull String key) {
    return sharedPreferences.getBoolean(key, BOOLEAN_DEFAULT);
  }

  protected void saveInt(@NonNull String key, int value) {
    sharedPreferences.edit().putInt(key, value).apply();
  }

  protected int getInt(@NonNull String key) {
    return sharedPreferences.getInt(key, INT_DEFAULT);
  }

  protected Map<String, ?> getAll() {
    return sharedPreferences.getAll();
  }
}
