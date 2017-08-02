package in.tanjo.httpintenter.adapter.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;

public class AbsListViewHolder<T> implements ViewHolder {

  public static final int INVALID_POSITION = -1;

  private final Context context;

  private final View view;

  @LayoutRes
  private final int resId;

  private int position;

  private Listener listener;

  public AbsListViewHolder(Context context, @LayoutRes int resId) {
    this.context = context;
    this.resId = resId;
    this.position = INVALID_POSITION;

    View view = View.inflate(this.context, this.resId, null);
    ButterKnife.bind(this, view);
    this.view = view;
  }

  public void bind(T t, int position) {
    this.position = position;
  }

  @NonNull
  @Override
  public View getView() {
    return view;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public void setListener(@Nullable Listener listener) {
    this.listener = listener;
  }

  public interface Listener {

    void onItemClick(int position);
  }
}
