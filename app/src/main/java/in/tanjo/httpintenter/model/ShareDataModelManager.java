package in.tanjo.httpintenter.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.httpintenter.HttpIntenterApplication;
import in.tanjo.httpintenter.event.UpdateShareDataModelEvent;
import in.tanjo.httpintenter.preference.HttpIntenterPreferenceManager;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class ShareDataModelManager {

  private static volatile ShareDataModelManager instance = null;

  private final HttpIntenterApplication application;

  private final List<ShareDataModel> shareDataModels;

  private final PublishSubject<Object> bus = PublishSubject.create();

  private ShareDataModelManager(HttpIntenterApplication application) {
    this.application = application;
    this.shareDataModels = new ArrayList<>();
  }

  /**
   * Application で初期化する
   * Application (Context) の保持が目的
   */
  public static void onCreateApplication(@NonNull HttpIntenterApplication application) {
    if (instance == null) {
      synchronized (ShareDataModelManager.class) {
        if (instance == null) {
          instance = new ShareDataModelManager(application);
        }
      }
    }
    getInstance().restore();
  }

  private void restore() {
    shareDataModels.addAll(new HttpIntenterPreferenceManager(application).getShareDataModels());
  }

  private static ShareDataModelManager getInstance() {
    if (instance == null) {
      throw new IllegalStateException("ShareDataModelManager should be initialized.");
    }
    return instance;
  }

  /**
   * 配列を設定する
   */
  public static void set(@NonNull List<ShareDataModel> shareDataModels) {
    getInstance().shareDataModels.addAll(shareDataModels);
    getInstance().save();
    send(new UpdateShareDataModelEvent());
  }

  private void save() {
    new HttpIntenterPreferenceManager(application).saveShareModels(shareDataModels);
  }

  /**
   * イベント送信
   */
  public static void send(final Object event) {
    getInstance().bus.onNext(event);
  }

  /**
   * {@link ShareDataModel} を追加する
   */
  public static void add(@NonNull ShareDataModel shareDataModel) {
    getInstance().shareDataModels.add(shareDataModel);
    getInstance().save();
    send(new UpdateShareDataModelEvent());
  }

  /**
   * {@link ShareDataModel} を消す
   */
  public static void clear() {
    getInstance().shareDataModels.clear();
    getInstance().save();
    send(new UpdateShareDataModelEvent());
  }

  /**
   * {@link ShareDataModel} のリストを取得する
   */
  public static List<ShareDataModel> get() {
    return getInstance().shareDataModels;
  }

  /**
   * イベント受取
   */
  public static Observable<Object> toObservable() {
    return getInstance().bus;
  }

  /**
   * Observer を所持しているか (アプリが表示されているかどうかがわかる)
   */
  public static boolean hasObservers() {
    return getInstance().bus.hasObservers();
  }
}
