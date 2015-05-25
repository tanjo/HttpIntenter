package in.tanjo.httpintenter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
  private StringUtils() {
  }

  public static String fromCharSequence(CharSequence cs) {
    if (cs != null) {
      return cs.toString();
    }
    return null;
  }

  public static final String URL_PATTERN = "(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+";

  public static Pattern getUrlPattern() {
    return Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
  }

  public static String getHttp(String text) {
    Matcher matcher = getUrlPattern().matcher(text);
    if (matcher.find()) {
      String url = matcher.group();
      if (url != null && url != "") {
        return matcher.group();
      }
    }
    return null;
  }
}
