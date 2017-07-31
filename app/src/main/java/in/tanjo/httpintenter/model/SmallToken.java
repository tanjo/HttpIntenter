package in.tanjo.httpintenter.model;

import com.atilika.kuromoji.ipadic.Token;

public class SmallToken {

  public String surface;

  public String type;

  public SmallToken(Token token) {
    if (token == null) {
      return;
    }
    surface = token.getSurface();
    if (token.getAllFeaturesArray().length == 0) {
      return;
    }
    type = token.getAllFeaturesArray()[0];
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
}
