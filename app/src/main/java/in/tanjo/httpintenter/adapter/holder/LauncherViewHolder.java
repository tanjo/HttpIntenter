package in.tanjo.httpintenter.adapter.holder;

import android.content.Context;
import android.widget.TextView;

import butterknife.BindView;
import in.tanjo.httpintenter.R;
import io.reactivex.Observable;

import static in.tanjo.httpintenter.util.ViewUtils.setTextObservable;

public class LauncherViewHolder extends AbsListViewHolder<String> {

  @BindView(R.id.viewholder_launcher_textview)
  TextView textView;

  public LauncherViewHolder(Context context) {
    super(context, R.layout.viewholder_launcher);
  }

  @Override
  public void bind(String s, int position) {
    super.bind(s, position);

    setTextObservable(textView, Observable.just(s));
  }
}
