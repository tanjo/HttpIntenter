package in.tanjo.httpintenter.util;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ViewUtils {

  private ViewUtils() {
    // no instance.
  }

  /**
   * {@link ViewUtils#setTextObservable(TextView, Observable)}
   */
  public static Disposable setTextObservable(@NonNull TextView textView, String text) {
    return setTextObservable(textView, Observable.just(StringUtils.nullToEmpty(text)));
  }

  /**
   * {@link TextView}に文字列を設定する.
   * {@link NullPointerException} などのエラーが起きた場合は非表示にします.
   */
  public static Disposable setTextObservable(@NonNull TextView textView, Observable<String> observable) {
    return observable
        .flatMap(s -> StringUtils.isNullOrEmpty(s) ? Observable.error(new NullPointerException()) : Observable.just(s))
        .subscribe(text -> {
          textView.setText(text);
          textView.setVisibility(View.VISIBLE);
        }, throwable -> {
          textView.setVisibility(View.GONE);
        });
  }
}
