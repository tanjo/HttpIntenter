package in.tanjo.httpintenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.model.UrlModel;

public class UrlContentAdapter extends ArrayAdapter<UrlModel> {
  private static final String TAG = UrlContentAdapter.class.getSimpleName();

  private LayoutInflater mLayoutInflater;

  public static class ViewHolder {
    @Bind(R.id.url_content_cell_title) TextView mTitleView;
    @Bind(R.id.url_content_cell_url) TextView mUrlView;
    @Bind(R.id.url_content_cell_json) TextView mJsonView;

    public ViewHolder(View v) {
      ButterKnife.bind(this, v);
    }

    public TextView getTitleView() {
      return mTitleView;
    }

    public TextView getUrlView() {
      return mUrlView;
    }

    public TextView getJsonView() {
      return mJsonView;
    }
  }

  public UrlContentAdapter(Context context, int resource, List<UrlModel> objects) {
    super(context, resource, objects);

    mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;

    if (convertView == null) {
      convertView = mLayoutInflater.inflate(R.layout.url_content_cell_layout, null);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    UrlModel urlModel = getItem(position);
    if (urlModel != null && holder.getTitleView() != null) {
      holder.getTitleView().setText(urlModel.getTitle());
      holder.getUrlView().setText(urlModel.getUrl());
      holder.getJsonView().setText(urlModel.toJson());
    }

    return convertView;
  }
}