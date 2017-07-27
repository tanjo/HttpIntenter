package in.tanjo.httpintenter;

import android.app.Application;

import in.tanjo.httpintenter.model.ShareDataModelManager;


public class HttpIntenterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ShareDataModelManager.onCreateApplication(this);
  }
}
