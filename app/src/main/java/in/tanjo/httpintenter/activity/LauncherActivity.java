package in.tanjo.httpintenter.activity;

import com.jakewharton.rxbinding2.widget.RxAdapterView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.LauncherAdapter;
import in.tanjo.httpintenter.model.ShareDataModel;
import in.tanjo.httpintenter.util.GsonUtils;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

public class LauncherActivity extends AppCompatActivity {

  @BindView(R.id.activity_launcher_listview)
  ListView listView;

  private BehaviorSubject<ShareDataModel> shareDataModel = BehaviorSubject.create();

  private BehaviorSubject<List<ResolveInfo>> appList = BehaviorSubject.create();

  private LauncherAdapter launcherAdapter;

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  private BehaviorSubject<PackageManager> packageManager = BehaviorSubject.create();

  private BehaviorSubject<Intent> intent = BehaviorSubject.create();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    ButterKnife.bind(this);
    launcherAdapter = new LauncherAdapter(this);
    listView.setAdapter(launcherAdapter);
    subscribe();
    packageManager.onNext(getPackageManager());
    intent.onNext(getIntent());
  }

  /**
   * RxJava-related subscribe
   */
  private void subscribe() {
    // Item Click 時に遷移する
    compositeDisposable.add(Observable
        .combineLatest(Observable.combineLatest(appList, RxAdapterView.itemClicks(listView), List::get),
            shareDataModel.map(model -> model.url).map(Uri::parse),
            this::createIntentWithPackage)
        .subscribe(this::startActivity, Throwable::printStackTrace, this::finish)
    );

    // AppList (String) のリスト作成
    compositeDisposable.add(Observable
        .combineLatest(appList, packageManager, this::createAppListStrings)
        .subscribe(launcherAdapter::set, throwable -> launcherAdapter.set(Collections.emptyList()))
    );

    // ShareDataModel から AppList を作成
    compositeDisposable.add(Observable
        .combineLatest(shareDataModel.map(m -> m.url).map(Uri::parse).map(this::createIntentWithUri),
            packageManager, (i, pm) -> pm.queryIntentActivities(i, 0))
        .subscribe(appList::onNext, appList::onError)
    );

// URL を検索とかもできそう(形態素解析しないと)
//    compositeDisposable.add(Observable
//        .combineLatest(
//            Observable.combineLatest(
//                Observable.just(createIntentWithWebSearch()),
//                packageManager,
//                (i, pm) -> pm.queryIntentActivities(i, 0)),
//            packageManager,
//            this::createAppListStrings)
//        .subscribe(launcherAdapter::addAll, throwable -> launcherAdapter.set(Collections.emptyList()))
//    );

    // Intent から ShareDataModel を取得
    compositeDisposable.add(intent
        .map(i -> i.getStringExtra(ShareDataModel.EXTRA_SHARE_DATA_MODEL))
        .map(s -> GsonUtils.getGson().fromJson(s, ShareDataModel.class))
        .subscribe(shareDataModel::onNext, shareDataModel::onError)
    );
  }

  /**
   * Intent#ACTION_WEB_SEARCH
   */
  private Intent createIntentWithWebSearch() {
    return new Intent(Intent.ACTION_WEB_SEARCH);
  }

  @Override
  protected void onDestroy() {
    compositeDisposable.clear();
    super.onDestroy();
  }

  /**
   * {@link ResolveInfo} を元に package を設定した {@link Intent} を返す
   */
  private Intent createIntentWithPackage(ResolveInfo info, Uri uri) {
    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    intent.setPackage(info.activityInfo.packageName);
    return intent;
  }

  /**
   * Intent#ACTION_VIEW に URI を設定した {@link Intent} を返す
   */
  private Intent createIntentWithUri(Uri uri) {
    return new Intent(Intent.ACTION_VIEW, uri);
  }

  /**
   * {@link ResolveInfo} と {@link PackageManager} から AppList を作成する
   */
  private List<String> createAppListStrings(List<ResolveInfo> resolveInfos, PackageManager packageManager) {
    return Observable.fromIterable(resolveInfos)
        .map(info -> info.loadLabel(packageManager).toString())
        .toList()
        .blockingGet();
  }
}
