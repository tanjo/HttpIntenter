package in.tanjo.httpintenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.UrlContentAdapter;
import in.tanjo.httpintenter.model.UrlModel;
import in.tanjo.httpintenter.model.UrlModelManager;

public class FirstActivity extends Activity {

  @Bind(R.id.first_activity_listview) ListView mListView;

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
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
  }
}
