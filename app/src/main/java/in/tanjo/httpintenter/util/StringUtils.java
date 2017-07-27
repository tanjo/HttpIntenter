package in.tanjo.httpintenter.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  private static final String URL_PATTERN = "(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+";

  private StringUtils() {
    // no instance.
  }

  /**
   * 文字列からURLを取得する
   */
  @Nullable
  public static String getUrl(String text) {
    if (text == null) {
      return null;
    }
    Matcher matcher = getUrlPattern().matcher(text);
    if (matcher.find()) {
      String url = matcher.group();
      if (url != null && !url.equals("")) {
        return matcher.group();
      }
    }
    return null;
  }

  @NonNull
  private static Pattern getUrlPattern() {
    return Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
  }

  @NonNull
  public static String nullToEmpty(String string) {
    if (string == null) {
      return "";
    }
    return string;
  }

  public static boolean isNullOrEmpty(String string) {
    return string == null || string.length() == 0;
  }
}
