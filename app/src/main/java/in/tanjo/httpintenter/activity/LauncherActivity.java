package in.tanjo.httpintenter.activity;

import com.jakewharton.rxbinding2.widget.RxAdapterView;

import android.content.Intent;
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

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launcher);
    ButterKnife.bind(this);

    launcherAdapter = new LauncherAdapter(this);
    listView.setAdapter(launcherAdapter);

    compositeDisposable
        .add(Observable.combineLatest(Observable.combineLatest(appList, RxAdapterView.itemClicks(listView), List::get),
            shareDataModel.map(model -> model.url).map(Uri::parse),
            this::createIntentWithPackage)
            .subscribe(this::startActivity, Throwable::printStackTrace, this::finish));

    compositeDisposable.add(Observable
        .combineLatest(appList.flatMapIterable(resolveInfos -> resolveInfos), Observable.just(getPackageManager()),
            (info, packageManager) -> info.loadLabel(packageManager).toString())
        .toList()
        .subscribe(launcherAdapter::set, throwable -> launcherAdapter.set(Collections.emptyList())));

    compositeDisposable.add(Observable
        .combineLatest(shareDataModel.map(m -> m.url).map(Uri::parse).map(uri -> new Intent(Intent.ACTION_VIEW, uri)),
            Observable.just(getPackageManager()), (i, pm) -> pm.queryIntentActivities(i, 0))
        .subscribe(appList::onNext, appList::onError));

    compositeDisposable.add(Observable
        .just(getIntent())
        .map(intent -> intent.getStringExtra(ShareDataModel.EXTRA_SHARE_DATA_MODEL))
        .map(s -> GsonUtils.getGson().fromJson(s, ShareDataModel.class))
        .subscribe(shareDataModel::onNext, shareDataModel::onError));
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
}
