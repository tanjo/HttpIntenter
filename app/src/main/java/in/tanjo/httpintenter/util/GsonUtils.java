package in.tanjo.httpintenter.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

  private GsonUtils() {
    // no instance.
  }

  public static <T> String toJson(T t) {
    String json = "";
    try {
      json = getGson().toJson(t);
    } catch (Exception ignored) {
    }
    return StringUtils.nullToEmpty(json);
  }

  public static Gson getGson() {
    return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  }
}
