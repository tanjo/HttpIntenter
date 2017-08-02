package in.tanjo.httpintenter.adapter.holder;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.model.license.License;
import in.tanjo.httpintenter.util.StringUtils;
import io.reactivex.Observable;

public class LicenseViewHolder extends AbsRecyclerViewHolder<License> {

  @BindView(R.id.viewholder_license_library_name)
  TextView libraryNameView;

  @BindView(R.id.viewholder_license_library_description)
  TextView libraryDescriptionView;

  @BindView(R.id.viewholder_license_library_url)
  TextView libraryUrlView;

  @BindView(R.id.viewholder_license_copyright)
  TextView copyrightView;

  @BindView(R.id.viewholder_license_type)
  TextView typeView;

  private int position;

  public LicenseViewHolder(View itemView, @Nullable Listener listener) {
    super(itemView, listener);
  }

  @Override
  public void bind(License license, int position) {
    this.position = position;
    setText(license.library.name, libraryNameView);
    setText(license.library.description, libraryDescriptionView);
    setText(license.library.url, libraryUrlView);
    setText(license.getCopyRight(), copyrightView);
    setText(license.type.getName(), typeView);
  }

  private void setText(String text, final TextView textView) {
    Observable.just(StringUtils.nullToEmpty(text))
        .flatMap(s -> StringUtils.isNullOrEmpty(s) ? Observable.error(new NullPointerException()) : Observable.just(s))
        .doOnNext(s -> textView.setVisibility(View.VISIBLE))
        .doOnError(throwable -> textView.setVisibility(View.GONE))
        .subscribe(textView::setText, Throwable::printStackTrace);
  }

  private void setText(@StringRes int res, final TextView textView) {
    textView.setText(res);
  }

  @OnClick(R.id.viewholder_license_linearlayout)
  void onClick() {
    Listener listener = getListener();
    if (listener == null) {
      return;
    }
    listener.onItemClick(position);
  }
}
