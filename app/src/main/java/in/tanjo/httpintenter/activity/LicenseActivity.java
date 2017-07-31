package in.tanjo.httpintenter.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.LicenseAdapter;
import in.tanjo.httpintenter.model.license.License;
import in.tanjo.httpintenter.model.license.Licenses;
import in.tanjo.httpintenter.util.StringUtils;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;


public class LicenseActivity extends AppCompatActivity {

  private static final List<License> LICENSES = Collections.unmodifiableList(new LinkedList<License>() {{
    add(Licenses.butterKnife);
    add(Licenses.gson);
    add(Licenses.rxJava);
    add(Licenses.rxAndroid);
    add(Licenses.rxBinding);
    add(Licenses.kuromoji);
    add(Licenses.kuromojiIpadic);
  }});

  @BindView(R.id.activity_license_recyclerview)
  RecyclerView recyclerView;

  BehaviorSubject<List<License>> licenses = BehaviorSubject.create();

  CompositeDisposable compositeDisposable = new CompositeDisposable();

  LicenseAdapter adapter = new LicenseAdapter();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_license);
    ButterKnife.bind(this);
    adapter.setListener(this::onClick);
    recyclerView.setAdapter(adapter);
    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
        new LinearLayoutManager(this).getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    compositeDisposable.add(licenses.subscribe(adapter::setLicenses, throwable -> adapter.clearLicenses()));
    licenses.onNext(LICENSES);
  }

  @Override
  protected void onDestroy() {
    compositeDisposable.clear();
    super.onDestroy();
  }

  private void onClick(int position, License license) {
    compositeDisposable.add(Observable
        .just(license)
        .filter(l -> !StringUtils.isNullOrEmpty(l.library.url))
        .map(l -> l.library.url)
        .map(Uri::parse)
        .subscribe(uri -> startActivity(new Intent(Intent.ACTION_VIEW, uri)), Throwable::printStackTrace));
  }

  public static Intent createIntent(@NonNull Context context) {
    return new Intent(context, LicenseActivity.class);
  }
}
