package in.tanjo.httpintenter.adapter.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


public abstract class AbsRecyclerViewHolder<Item> extends RecyclerView.ViewHolder implements ViewHolder {

  private View view;

  @Nullable
  private Listener listener;

  public AbsRecyclerViewHolder(View itemView, @Nullable Listener listener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    this.listener = listener;
  }

  @Nullable
  public Listener getListener() {
    return listener;
  }

  @NonNull
  @Override
  public View getView() {
    return view;
  }

  public abstract void bind(Item item, int position);

  public interface Listener {

    void onItemClick(int position);
  }
}
