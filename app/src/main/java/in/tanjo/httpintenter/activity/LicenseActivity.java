package in.tanjo.httpintenter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.model.license.License;
import in.tanjo.httpintenter.model.license.Licenses;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;


public class LicenseActivity extends AppCompatActivity {

  @BindView(R.id.activity_license_listview)
  ListView listView;

  BehaviorSubject<List<License>> licenses = BehaviorSubject.create();

  CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_license);
    ButterKnife.bind(this);



    licenses.onNext(Licenses.get());
  }
}
