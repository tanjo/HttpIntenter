package in.tanjo.httpintenter.adapter.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.model.SmallToken;
import in.tanjo.httpintenter.util.StringUtils;
import io.reactivex.Observable;

public class SearchViewHolder extends AbsRecyclerViewHolder<SmallToken> {

  @BindView(R.id.viewholder_search_surface)
  TextView surfaceView;

  @BindView(R.id.viewholder_search_type)
  TextView typeView;

  private int position;

  public SearchViewHolder(View itemView, @Nullable Listener listener) {
    super(itemView, listener);
  }

  @Override
  public void bind(SmallToken smallToken, int position) {
    this.position = position;
    setText(smallToken.surface, surfaceView);
    setText(smallToken.type, typeView);
  }

  private void setText(String text, final TextView textView) {
    Observable.just(StringUtils.nullToEmpty(text))
        .flatMap(s -> StringUtils.isNullOrEmpty(s) ? Observable.error(new NullPointerException()) : Observable.just(s))
        .doOnNext(s -> textView.setVisibility(View.VISIBLE))
        .doOnError(throwable -> textView.setVisibility(View.GONE))
        .subscribe(textView::setText, Throwable::printStackTrace);
  }

  @OnClick(R.id.viewholder_search_linearlayout)
  void onClick() {
    Listener listener = getListener();
    if (listener == null) {
      return;
    }
    listener.onItemClick(position);
  }
}
