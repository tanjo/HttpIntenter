package in.tanjo.httpintenter.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListLuncherActivity extends ListActivity {

  public static final String EXTRA_URL = "list_launcher_activity_extra_url";

  private Uri mUri;

  private List<ResolveInfo> mAppInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent receivedIntent = getIntent();
    if (receivedIntent != null) {
      String url = receivedIntent.getStringExtra(EXTRA_URL);
      mUri = Uri.parse(url);
    }

    ArrayList<String> appList = new ArrayList<String>();
    PackageManager packageManager = getPackageManager();

    Intent i = new Intent(Intent.ACTION_VIEW, mUri);
    mAppInfo = packageManager.queryIntentActivities(i, 0);
    if (mAppInfo != null) {
      for (ResolveInfo info : mAppInfo) {
        appList.add((String) info.loadLabel(packageManager));
      }
      ;
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appList);
    setListAdapter(adapter);
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    ResolveInfo info = mAppInfo.get(position);
    Intent intent = new Intent(Intent.ACTION_VIEW, mUri);
    intent.setPackage(info.activityInfo.packageName);
    startActivity(intent);
    finish();
  }
}
