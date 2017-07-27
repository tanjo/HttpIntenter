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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;


public class MainActivity extends AppCompatActivity {

  @BindView(R.id.activity_main_listview)
  ListView listView;

  MainAdapter mainAdapter;

  private BehaviorSubject<List<ShareDataModel>> shareDataModels = BehaviorSubject.create();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mainAdapter = new MainAdapter(this);

    shareDataModels.subscribe(mainAdapter::set, Throwable::printStackTrace);

    ShareDataModelManager.toObservable()
        .observeOn(AndroidSchedulers.mainThread())
        .filter(object -> object instanceof UpdateShareDataModelEvent)
        .map(event -> (UpdateShareDataModelEvent) event)
        .subscribe(updateShareDataModelEvent -> shareDataModels.onNext(ShareDataModelManager.get()));

    RxAdapterView.itemClicks(listView)
        .zipWith(shareDataModels, (i, list) -> list.get(i))
        .filter(shareDataModel -> shareDataModel.openLuncherActivity(this))
        .subscribe(shareDataModel -> finish(), Throwable::printStackTrace);

    shareDataModels.onNext(ShareDataModelManager.get());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    RxMenuItem.clicks(menu.add(0, 100, 0, "Delete All"))
        .subscribe(o -> ShareDataModelManager.clear(), Throwable::printStackTrace);
    return true;
  }
}
