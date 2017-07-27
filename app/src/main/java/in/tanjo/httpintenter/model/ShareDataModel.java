package in.tanjo.httpintenter.model;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import in.tanjo.httpintenter.activity.LauncherActivity;
import in.tanjo.httpintenter.util.GsonUtils;
import in.tanjo.httpintenter.util.StringUtils;

public class ShareDataModel {

  public static final String EXTRA_SHARE_DATA_MODEL = "share_data_model_extra_url";

  public String url;

  public String packageName;

  public String className;

  public String text;

  public String subject;

  public String title;

  public String installerPackageName;

  public int flags;

  public ShareDataModel(@Nullable Intent intent, @Nullable ComponentName callingActivity) {
    if (intent != null && Intent.ACTION_SEND.equals(intent.getAction())) {
      setupIntent(intent);
    }
    if (callingActivity != null) {
      initCallingActivity(callingActivity);
    }
  }

  private void setupIntent(@NonNull Intent intent) {
    flags = intent.getFlags();
    Bundle extra = intent.getExtras();
    if (extra == null) {
      return;
    }
    setupExtra(extra);
  }

  private void initCallingActivity(@NonNull ComponentName componentName) {
    packageName = componentName.getPackageName();
    className = componentName.getClassName();
  }

  /**
   * Extraから取得できるデータを設定
   */
  private void setupExtra(@NonNull Bundle extra) {
    text = extra.getString(Intent.EXTRA_TEXT);
    subject = extra.getString(Intent.EXTRA_SUBJECT);
    title = extra.getString(Intent.EXTRA_TITLE);
    installerPackageName = extra.getString(Intent.EXTRA_INSTALLER_PACKAGE_NAME);
    setupUrl();
  }

  /**
   * URLを取得・設定
   * text -> title -> subject の順でチェック
   */
  private void setupUrl() {
    url = StringUtils.getUrl(text);
    if (url == null) {
      url = StringUtils.getUrl(title);
    }
    if (url == null) {
      url = StringUtils.getUrl(subject);
    }
  }

  /**
   * ListLauncherActivity で URLを開く
   */
  public boolean openLuncherActivity(@NonNull Context context) {
    return open(context, LauncherActivity.class, url);
  }

  private static <T extends Class> boolean open(@NonNull Context context, @NonNull T tClass, @NonNull String url) {
    if (StringUtils.isNullOrEmpty(url)) {
      return false;
    }
    Intent intent = createIntent(context, tClass, url);
    if (intent == null) {
      return false;
    }
    context.startActivity(intent);
    return true;
  }

  private static <T extends Class> Intent createIntent(@NonNull Context context, @NonNull T tClass, @NonNull String url) {
    Intent intent = new Intent(context, tClass);
    intent.putExtra(EXTRA_SHARE_DATA_MODEL, url);
    return intent;
  }

  public String toJson() {
    return GsonUtils.toJson(this);
  }
}
