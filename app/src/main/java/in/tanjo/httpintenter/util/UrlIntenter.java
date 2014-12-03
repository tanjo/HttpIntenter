package in.tanjo.httpintenter.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by tanjo on 2014/12/03.
 */
public class UrlIntenter {

  public enum BrowserType {
    DefaultBrowser("com.android.browser.BrowserActivity", "com.android.browser", 50855937),
    Chrome("", "com.android.chrome", 50331649),
    None(null, null, 0);

    private final String className;
    private final String packageName;
    private int flags;

    private BrowserType(final String className, final String packageName, final int flags) {
      this.className = className;
      this.packageName = packageName;
      this.flags = flags;
    }

    public String getClassName() {
      return className;
    }

    public String getPackageName() {
      return packageName;
    }

    public int getFlags() {
      return flags;
    }
  }


  private Context mContext;

  public UrlIntenter(Context context) {
    mContext = context;
  }

  public boolean open(String url, BrowserType type) {
    if (url == null || url == "") {
      return false;
    }
    Uri uri = Uri.parse(url);
    return open(uri, type);
  }

  public boolean open(Uri uri, BrowserType type) {
    if (uri == null) {
      return false;
    }
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    switch (type) {
      case DefaultBrowser:
//        intent.setClassName(BrowserType.DefaultBrowser.getPackageName(), BrowserType.DefaultBrowser.getClassName());
        intent.setPackage(BrowserType.DefaultBrowser.getPackageName());
        break;
      case Chrome:
//        intent.setClassName(BrowserType.Chrome.getPackageName(), BrowserType.Chrome.getClassName());
        intent.setPackage(BrowserType.Chrome.getPackageName());
        break;
      case None:
        // 何もしない
        break;
      default:
        break;
    }
    mContext.startActivity(intent);
    return true;
  }
}
