package in.tanjo.httpintenter.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import in.tanjo.httpintenter.util.StringUtils;

public class UrlModel {

  @SerializedName("url") String mUrl;
  @SerializedName("title") String mTitle;
  @SerializedName("subject") String mSubject;
  @SerializedName("text") String mText;
  @SerializedName("installer_package_name") String mInstallerPackageName;
  @SerializedName("flags") int mFlags;
  @SerializedName("package") String mPackageName;
  @SerializedName("class") String mClassName;

  public UrlModel() {
  }

  public UrlModel(Bundle extras, int flags, String packageName, String className) {
    save(extras, flags, packageName, className);
  }

  public static String toJson(UrlModel urlModel) {
    return new Gson().toJson(urlModel, UrlModel.class);
  }

  public static UrlModel fromJson(String json) {
    return new Gson().fromJson(json, UrlModel.class);
  }

  public String toJson() {
    return toJson(this);
  }

  public String getUrl() {
    return StringUtils.convertNullToEmptyString(mUrl);
  }

  public void setUrl(String url) {
    mUrl = url;
  }

  public String getTitle() {
    return StringUtils.convertNullToEmptyString(mTitle);
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getSubject() {
    return StringUtils.convertNullToEmptyString(mSubject);
  }

  public void setSubject(String subject) {
    mSubject = subject;
  }

  public String getText() {
    return StringUtils.convertNullToEmptyString(mText);
  }

  public void setText(String text) {
    mText = text;
  }

  public String getInstallerPackageName() {
    return StringUtils.convertNullToEmptyString(mInstallerPackageName);
  }

  public void setInstallerPackageName(String installerPackageName) {
    mInstallerPackageName = installerPackageName;
  }

  public int getFlags() {
    return mFlags;
  }

  public void setFlags(int flags) {
    mFlags = flags;
  }

  public String getPackageName() {
    return StringUtils.convertNullToEmptyString(mPackageName);
  }

  public void setPackageName(String aPackage) {
    mPackageName = aPackage;
  }

  public String getClassName() {
    return mClassName;
  }

  public void setClassName(String className) {
    mClassName = className;
  }

  public BrowserType getBrowserType() {
    return BrowserType.fromFlags(getFlags());
  }

  public void save(Bundle extras, int flags, String packageName, String className) {
    if (extras != null) {
      setText(StringUtils.fromCharSequence(extras.getCharSequence(Intent.EXTRA_TEXT)));
      setSubject(StringUtils.fromCharSequence(extras.getCharSequence(Intent.EXTRA_SUBJECT)));
      setTitle(StringUtils.fromCharSequence(extras.getCharSequence(Intent.EXTRA_TITLE)));
      setInstallerPackageName(StringUtils.fromCharSequence(extras.getCharSequence(Intent.EXTRA_INSTALLER_PACKAGE_NAME)));
      setFlags(flags);
      setUrl(StringUtils.getHttp(getText()));
      setPackageName(packageName);
      setClassName(className);
    }
  }

  public boolean open(Context activity) {
    return open(activity, getUrl(), getBrowserType());
  }

  public boolean open(Context activity, String uri, BrowserType browserType) {
    if (uri == null) {
      return false;
    }
    Intent intent = browserType.nextIntent(uri);
    if (intent == null) {
      return false;
    }
    activity.startActivity(intent);
    return true;
  }
}
