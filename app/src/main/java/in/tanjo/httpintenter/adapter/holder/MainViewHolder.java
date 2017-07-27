package in.tanjo.httpintenter.adapter.holder;

import android.content.Context;
import android.widget.TextView;

import butterknife.BindView;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.model.ShareDataModel;
import io.reactivex.Observable;

import static in.tanjo.httpintenter.util.ViewUtils.setTextObservable;

public class MainViewHolder extends AbsListViewHolder<ShareDataModel> {

  @BindView(R.id.viewholder_main_textview)
  TextView textView;

  @BindView(R.id.viewholder_main_titleview)
  TextView titleView;

  @BindView(R.id.viewholder_main_urlview)
  TextView urlView;

  @BindView(R.id.viewholder_main_subjectview)
  TextView subjectView;

  @BindView(R.id.viewholder_main_jsonview)
  TextView jsonView;

  @BindView(R.id.viewholder_main_flagsview)
  TextView flagsView;

  public MainViewHolder(Context context) {
    super(context, R.layout.viewholder_main);
  }

  @Override
  public void bind(ShareDataModel shareDataModel, int position) {
    super.bind(shareDataModel, position);

    setTextObservable(urlView, shareDataModel.url);
    setTextObservable(titleView, shareDataModel.title);
    setTextObservable(textView, shareDataModel.text);
    setTextObservable(subjectView, shareDataModel.subject);
    setTextObservable(jsonView, Observable.just(shareDataModel.toJson()));
    setTextObservable(flagsView, Observable.just(shareDataModel.flags).map(String::valueOf));
  }
}
