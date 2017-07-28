package in.tanjo.httpintenter.activity;

import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.MainAdapter;
import in.tanjo.httpintenter.event.UpdateShareDataModelEvent;
import in.tanjo.httpintenter.model.ShareDataModel;
import in.tanjo.httpintenter.model.ShareDataModelManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;


public class MainActivity extends AppCompatActivity {

  @BindView(R.id.activity_main_listview)
  ListView listView;

  MainAdapter mainAdapter;

  private BehaviorSubject<List<ShareDataModel>> shareDataModels = BehaviorSubject.create();

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mainAdapter = new MainAdapter(this);
    listView.setAdapter(mainAdapter);

    compositeDisposable.add(shareDataModels.subscribe(mainAdapter::set, Throwable::printStackTrace));

    compositeDisposable.add(ShareDataModelManager
        .toObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .filter(object -> object instanceof UpdateShareDataModelEvent)
        .map(event -> (UpdateShareDataModelEvent) event)
        .subscribe(updateShareDataModelEvent -> shareDataModels.onNext(ShareDataModelManager.get())));

    compositeDisposable.add(Observable
        .combineLatest(shareDataModels, RxAdapterView.itemClicks(listView), List::get)
        .subscribe(shareDataModel -> shareDataModel.openLuncherActivity(this), Throwable::printStackTrace));

    shareDataModels.onNext(ShareDataModelManager.get());
  }

  @Override
  protected void onDestroy() {
    compositeDisposable.clear();
    super.onDestroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    compositeDisposable.add(RxMenuItem
        .clicks(menu.add(0, 100, 0, "Delete All"))
        .subscribe(o -> ShareDataModelManager.clear(), Throwable::printStackTrace));
    compositeDisposable.add(RxMenuItem
        .clicks(menu.add(0, 101, 1, "License"))
        .subscribe(o -> startActivity(LicenseActivity.createIntent(this))));
    return true;
  }
}
