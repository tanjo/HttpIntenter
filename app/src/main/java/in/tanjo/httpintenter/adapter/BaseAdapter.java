package in.tanjo.httpintenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import in.tanjo.httpintenter.adapter.holder.ViewHolder;


public abstract class BaseAdapter<T, VH extends ViewHolder> extends ArrayAdapter<T> {

  public BaseAdapter(@NonNull Context context) {
    super(context, 0);
  }

  /**
   * 既存のデータを更新する
   */
  public boolean update(@NonNull T t) {
    int position = getPosition(t);
    if (position < 0) {
      return false;
    }
    remove(t);
    insert(t, position);
    return true;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    VH viewHolder;
    if (convertView == null) {
      viewHolder = newViewHolder(getContext(), position);
      convertView = viewHolder.getView();
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (VH) convertView.getTag();
    }
    bindViewHolder(position, viewHolder, parent, getItem(position));
    return convertView;
  }

  public abstract VH newViewHolder(@NonNull Context context, int poisition);

  public abstract void bindViewHolder(int posiition, @NonNull VH viewHolder, ViewGroup parent, T t);
}
