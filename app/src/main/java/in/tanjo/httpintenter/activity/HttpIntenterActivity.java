package in.tanjo.httpintenter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.fragment.HttpIntenterFragment;
import in.tanjo.httpintenter.model.BrowserType;
import in.tanjo.httpintenter.model.UrlModel;
import in.tanjo.httpintenter.model.UrlModelManager;
import in.tanjo.httpintenter.util.StringUtils;

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
        String text = StringUtils.fromCharSequence(extras.getCharSequence(Intent.EXTRA_TEXT));
        String title = StringUtils.fromCharSequence(extras.getCharSequence(Intent.EXTRA_SUBJECT));

        BrowserType browserType = BrowserType.fromFlags(intent.getFlags());

        if (text != null) {
          String url = StringUtils.getHttp(text);
          if (url != null) {
            UrlModel urlModel = new UrlModel();
            urlModel.setTitle(title);
            urlModel.setUrl(url);

            UrlModelManager urlModelManager = new UrlModelManager(this);
            urlModelManager.restore();
            urlModelManager.getUrlModels().getItems().add(urlModel);
            urlModelManager.save();

            if (open(url, browserType)) {
              finish();
            }
          }
        }
      }
    }

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(R.id.container, new HttpIntenterFragment())
          .commit();
    }
  }

  public boolean open(String uri, BrowserType browserType) {
    if (uri == null) {
      return false;
    }
    Intent intent = browserType.nextIntent(uri);
    if (intent == null) {
      return false;
    }
    startActivity(intent);
    return true;
  }

}
