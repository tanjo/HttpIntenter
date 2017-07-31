package in.tanjo.httpintenter.model;

import com.atilika.kuromoji.ipadic.Token;

import in.tanjo.httpintenter.util.StringUtils;

public class SmallToken {

  public String surface;

  public String type;

  public SmallToken(Token token) {
    if (token == null) {
      return;
    }
    surface = StringUtils.nullToEmpty(token.getSurface());
    if (token.getAllFeaturesArray().length == 0) {
      type = "";
      return;
    }
    type = StringUtils.nullToEmpty(token.getAllFeaturesArray()[0]);
  }

  public SmallToken(String surface, String type) {
    this.surface = surface;
    this.type = type;
  }

  @Override
  public int hashCode() {
    int result = surface != null ? surface.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SmallToken that = (SmallToken) o;

    return surface != null ? surface.equals(that.surface)
        : that.surface == null && (type != null ? type.equals(that.type) : that.type == null);
  }

  /**
   * 長い文字列が一番上にくる
   */
  public int compareTo(SmallToken s2) {
    if (s2 == null) {
      return 1;
    }
    if (surface.length() > s2.surface.length()) {
      return -1;
    } else if (surface.length() == s2.surface.length()) {
      return 0;
    }
    return 1;
  }
}
