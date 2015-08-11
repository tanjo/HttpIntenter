package in.tanjo.httpintenter.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.fragment.HttpIntenterFragment;
import in.tanjo.httpintenter.model.UrlModel;
import in.tanjo.httpintenter.model.UrlModelManager;

public class HttpIntenterActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_http_intenter);

    Intent intent = getIntent();
    String action = intent.getAction();
    if (Intent.ACTION_SEND.equals(action)) {
      Bundle extras = intent.getExtras();
      if (extras != null) {

        ComponentName callingActivity = getCallingActivity();
        String packageName = null;
        String className = null;
        if (callingActivity != null) {
          packageName = callingActivity.getPackageName();
          className = callingActivity.getClassName();
        }

        UrlModel urlModel = new UrlModel(extras, intent.getFlags(), packageName, className);

        UrlModelManager urlModelManager = new UrlModelManager(this);
        urlModelManager.restore();
        urlModelManager.getUrlModels().getItems().add(urlModel);
        urlModelManager.save();

        if (urlModel.open(this)) {
          finish();
        }
      }
    }

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(R.id.container, new HttpIntenterFragment())
          .commit();
    }
  }
}
