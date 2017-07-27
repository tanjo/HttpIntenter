package in.tanjo.httpintenter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

import in.tanjo.httpintenter.adapter.holder.MainViewHolder;
import in.tanjo.httpintenter.model.ShareDataModel;

public class MainAdapter extends BaseAdapter<ShareDataModel, MainViewHolder> {

  public MainAdapter(@NonNull Context context) {
    super(context);
  }

  @Override
  public MainViewHolder newViewHolder(@NonNull Context context, int poisition) {
    return new MainViewHolder(context);
  }

  @Override
  public void bindViewHolder(int posiition, @NonNull MainViewHolder viewHolder, ViewGroup parent, ShareDataModel model) {
    viewHolder.bind(model, posiition);
  }

  public void set(List<ShareDataModel> shareDataModels) {
    clear();
    addAll(shareDataModels);
  }
}
