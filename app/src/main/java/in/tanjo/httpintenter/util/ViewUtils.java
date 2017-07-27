package in.tanjo.httpintenter.util;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;

public class ViewUtils {

  private ViewUtils() {
    // no instance.
  }

  /**
   * {@link ViewUtils#setTextObservable(TextView, Observable)}
   */
  public static void setTextObservable(@NonNull TextView textView, String text) {
    setTextObservable(textView, Observable.just(text));
  }

  /**
   * {@link TextView}に文字列を設定する.
   * {@link NullPointerException} などのエラーが起きた場合は非表示にします.
   */
  public static void setTextObservable(@NonNull TextView textView, Observable<String> observable) {
    observable.subscribe(text -> {
      textView.setText(text);
      textView.setVisibility(View.VISIBLE);
    }, throwable -> {
      textView.setVisibility(View.GONE);
    });
  }
}
