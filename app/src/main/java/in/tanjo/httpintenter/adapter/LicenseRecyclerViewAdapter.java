package in.tanjo.httpintenter.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.holder.LicenseViewHolder;
import in.tanjo.httpintenter.model.license.License;


public class LicenseRecyclerViewAdapter extends RecyclerView.Adapter<LicenseViewHolder> {

  @NonNull
  private List<License> licenses = new ArrayList<>();

  @Nullable
  private Listener listener;

  public void setLicenses(@NonNull List<License> licenses) {
    this.licenses = licenses;
    notifyDataSetChanged();
  }

  public void addLicenses(@NonNull List<License> licenses) {
    this.licenses.addAll(licenses);
    notifyDataSetChanged();
  }

  public void clearLicenses() {
    this.licenses.clear();
    notifyDataSetChanged();
  }

  public void setListener(@Nullable Listener listener) {
    this.listener = listener;
  }

  @Override
  public LicenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_license, parent, false);
    return new LicenseViewHolder(view, this::onLicenseClick);
  }

  @Override
  public void onBindViewHolder(LicenseViewHolder holder, int position) {
    License license = licenses.get(position);
    if (holder == null) {
      return;
    }
    holder.bind(license, position);
  }

  @Override
  public int getItemCount() {
    return licenses.size();
  }

  private void onLicenseClick(int position) {
    if (listener == null && 0 <= position && position < licenses.size()) {
      return;
    }
    listener.onItemClick(position, licenses.get(position));
  }

  public interface Listener {

    void onItemClick(int position, License license);
  }
}
