package in.tanjo.httpintenter.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import in.tanjo.httpintenter.util.StringUtils;

public class UrlModel {

  @SerializedName("url")
  String url;

  @SerializedName("title")
  String title;

  @SerializedName("subject")
  String subject;

  @SerializedName("text")
  String text;

  @SerializedName("installer_package_name")
  String installerPackageName;

  @SerializedName("flags")
  int flags;

  @SerializedName("package")
  String packageName;

  @SerializedName("class")
  String className;

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
    return StringUtils.convertNullToEmptyString(url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return StringUtils.convertNullToEmptyString(title);
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubject() {
    return StringUtils.convertNullToEmptyString(subject);
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return StringUtils.convertNullToEmptyString(text);
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getInstallerPackageName() {
    return StringUtils.convertNullToEmptyString(installerPackageName);
  }

  public void setInstallerPackageName(String installerPackageName) {
    this.installerPackageName = installerPackageName;
  }

  public int getFlags() {
    return flags;
  }

  public void setFlags(int flags) {
    this.flags = flags;
  }

  public String getPackageName() {
    return StringUtils.convertNullToEmptyString(packageName);
  }

  public void setPackageName(String aPackage) {
    packageName = aPackage;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
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
    Intent intent = browserType.nextIntent(uri, activity);
    if (intent == null) {
      return false;
    }
    activity.startActivity(intent);
    return true;
  }
}
