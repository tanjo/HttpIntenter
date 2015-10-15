package in.tanjo.httpintenter.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import in.tanjo.httpintenter.activity.ListLuncherActivity;

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

  public Intent nextIntent(String url, Context context) {
    if (url == null || url.equals("")) {
      return null;
    }
    Uri uri = Uri.parse(url);
    return nextIntent(uri, context);
  }

  public Intent nextIntent(Uri uri, Context context) {
    if (!equals(None)) {
      Intent intent = new Intent(Intent.ACTION_VIEW, uri);
      intent.setPackage(getPackageName());
      return intent;
    } else {
      Intent intent = new Intent(context, ListLuncherActivity.class);
      intent.putExtra(ListLuncherActivity.EXTRA_URL, uri.toString());
      return intent;
    }
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
