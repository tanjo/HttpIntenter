package in.tanjo.httpintenter.activity;

import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.UrlContentAdapter;
import in.tanjo.httpintenter.model.UrlModel;
import in.tanjo.httpintenter.model.UrlModelManager;
import io.reactivex.subjects.BehaviorSubject;

public class FirstLaunchActivity extends Activity {

  public static final int MENU_DELETE_LOG = 100;

  @Bind(R.id.first_activity_listview)
  ListView listView;

  private UrlContentAdapter urlContentAdapter;

  private BehaviorSubject<List<UrlModel>> urlModels = BehaviorSubject.create();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first);
    ButterKnife.bind(this);

    UrlModelManager urlModelManager = new UrlModelManager(this);
    urlModelManager.restore();

    urlModels.onNext(urlModelManager.getUrlModels().getItems());

    urlContentAdapter = new UrlContentAdapter(this, 0, Collections.emptyList());
    listView.setAdapter(urlContentAdapter);

    urlModels.subscribe(this::setAdapterData);

    RxAdapterView.itemClicks(listView)
        .zipWith(urlModels, (integer, urlModels1) -> urlModels1.get(integer))
        .filter(urlModel -> urlModel != null)
        .filter(urlModel -> urlModel.open(FirstLaunchActivity.this))
        .subscribe(urlModel -> finish());

    RxAdapterView.itemLongClicks(listView)
        .zipWith(urlModels, (integer, urlModels1) -> urlModels1.get(integer))
        .subscribe(this::openListLauncher);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    RxMenuItem.clicks(menu.add(0, MENU_DELETE_LOG, 0, "Delete All")).subscribe(o -> urlModels.onNext(Collections.emptyList()));
    return true;
  }

  /**
   * ListView の Adapter をセットする
   * @param urlModels セットするデータ
   */
  private void setAdapterData(List<UrlModel> urlModels) {
    urlContentAdapter.clear();
    urlContentAdapter.addAll(urlModels);
    urlContentAdapter.notifyDataSetChanged();
  }

  /**
   * {@link ListLuncherActivity} を開く
   * @param urlModel URL情報
   */
  private void openListLauncher(UrlModel urlModel) {
    Intent intent = new Intent(FirstLaunchActivity.this, ListLuncherActivity.class);
    intent.putExtra(ListLuncherActivity.EXTRA_URL, urlModel.getUrl());
    startActivity(intent);
  }
}
