package in.tanjo.httpintenter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.tanjo.httpintenter.model.UrlContent;
import in.tanjo.httpintenter.util.UrlIntenter;

public class MainActivity extends Activity implements UrlItemFragment.OnFragmentInteractionListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Intent intent = getIntent();
    String action = intent.getAction();
    if (Intent.ACTION_SEND.equals(action)) {
      Bundle extras = intent.getExtras();
      if (extras != null) {
        String text = null;
        CharSequence extText = extras.getCharSequence(Intent.EXTRA_TEXT);
        if (extText != null) {
          text = extText.toString();
        }
        String title = null;
        CharSequence extTitle = extras.getCharSequence(Intent.EXTRA_SUBJECT);
        if (extTitle != null) {
          title = extTitle.toString();
        }

        final Pattern urlPattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+"
            , Pattern.CASE_INSENSITIVE);

        UrlIntenter.BrowserType browserType = UrlIntenter.BrowserType.None;
        if (intent.getFlags() == UrlIntenter.BrowserType.DefaultBrowser.getFlags()) {
          browserType = UrlIntenter.BrowserType.DefaultBrowser;
        } else if (intent.getFlags() == UrlIntenter.BrowserType.Chrome.getFlags()) {
          browserType = UrlIntenter.BrowserType.Chrome;
        }

        if (text != null) {
          Matcher matcher = urlPattern.matcher(text);

          if (matcher.find()) {
            String url = matcher.group();
            if (url != null && url != "") {
              UrlContent urlContent = new UrlContent();
              urlContent.restoreUrlContent(this);
              urlContent.addItem(new UrlContent.UrlItem(url, title));
              urlContent.saveUrlContent(this);
              if (new UrlIntenter(this).open(url, browserType)) {
                finish();
              }
            }
          }
        }
      }
    }

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .add(R.id.container, new PlaceholderFragment())
          .commit();
    }
  }

  @Override
  public void onFragmentInteraction(String url, String text) {
    new UrlIntenter(this).open(url, UrlIntenter.BrowserType.None);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_urlitem, container, false);
      return rootView;
    }
  }
}
