package in.tanjo.httpintenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.UrlContentAdapter;
import in.tanjo.httpintenter.model.UrlModel;
import in.tanjo.httpintenter.model.UrlModelManager;

public class FirstActivity extends Activity {

  public static final int MENU_DELETE_LOG = 100;

  @Bind(R.id.first_activity_listview)
  ListView mListView;

  private UrlModelManager mUrlModelManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_first);
    ButterKnife.bind(this);

    mUrlModelManager = new UrlModelManager(this);
    mUrlModelManager.restore();

    UrlContentAdapter adapter = new UrlContentAdapter(this, 0, mUrlModelManager.getUrlModels().getItems());
    mListView.setAdapter(adapter);
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UrlModel urlModel = mUrlModelManager.getUrlModels().getItems().get(position);
        if (urlModel.open(FirstActivity.this)) {
          finish();
        }
      }
    });
    mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(FirstActivity.this, ListLuncherActivity.class);
        UrlModel urlModel = mUrlModelManager.getUrlModels().getItems().get(position);
        intent.putExtra(ListLuncherActivity.EXTRA_URL, urlModel.getUrl());
        startActivity(intent);
        return true;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_DELETE_LOG, 0, "Delete All");
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case MENU_DELETE_LOG:
        mUrlModelManager.delete();
        UrlContentAdapter adapter = (UrlContentAdapter) mListView.getAdapter();
        adapter.clear();
        adapter.notifyDataSetChanged();
        return true;
    }
    return false;
  }
}
