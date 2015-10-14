package in.tanjo.httpintenter.model;

import android.content.Intent;
import android.net.Uri;

public enum BrowserType {
  DefaultBrowser("com.android.browser.BrowserActivity", "com.android.browser", 50855937),
  Chrome("", "com.android.chrome", 50331649),
  None(null, null, 0);

  private String mClassName;
  private String mPackageName;
  private int mFlags;

  private BrowserType(String className, String packageName, int flags) {
    mClassName = className;
    mPackageName = packageName;
    mFlags = flags;
  }

  public String getClassName() {
    return mClassName;
  }

  public String getPackageName() {
    return mPackageName;
  }

  public int getFlags() {
    return mFlags;
  }

  public Intent nextIntent(String url) {
    if (url == null || url.equals("")) {
      return null;
    }
    Uri uri = Uri.parse(url);
    return nextIntent(uri);
  }

  public Intent nextIntent(Uri uri) {
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    if (!equals(None)) {
      intent.setPackage(getPackageName());
    }
    return intent;
  }

  public static BrowserType fromFlags(int flags) {
    if (flags == DefaultBrowser.getFlags()) {
      return DefaultBrowser;
    } else if (flags == Chrome.getFlags()) {
      return Chrome;
    } else {
      return None;
    }
  }

}
