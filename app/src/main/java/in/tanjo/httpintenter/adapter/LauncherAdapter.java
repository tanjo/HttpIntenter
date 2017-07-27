package in.tanjo.httpintenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import in.tanjo.httpintenter.adapter.holder.LauncherViewHolder;

public class LauncherAdapter extends BaseAdapter<String, LauncherViewHolder> {

  public LauncherAdapter(@NonNull Context context) {
    super(context);
  }

  @Override
  public LauncherViewHolder newViewHolder(@NonNull Context context, int poisition) {
    return new LauncherViewHolder(context);
  }

  @Override
  public void bindViewHolder(int posiition, @NonNull LauncherViewHolder viewHolder, ViewGroup parent, String s) {
    viewHolder.bind(s, posiition);
  }

  public void set(List<String> strings) {
    clear();
    addAll(strings);
  }
}
