package in.tanjo.httpintenter.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.model.ShareDataModel;
import in.tanjo.httpintenter.model.ShareDataModelManager;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;


public class ReceiveIntentActivity extends AppCompatActivity {

  BehaviorSubject<ShareDataModel> shareDataModelSubject = BehaviorSubject.create();

  @BindView(R.id.activity_receive_intent_textview)
  TextView textView;

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receive_intent);
    ButterKnife.bind(this);

    compositeDisposable.add(shareDataModelSubject.subscribe(this::openLauncher, this::showThrowable));
    compositeDisposable.add(shareDataModelSubject.subscribe(this::saveShareDataModel, this::showThrowable));

    shareDataModelSubject.onNext(new ShareDataModel(getIntent(), getCallingActivity()));
  }

  /**
   * {@link TextView} にエラーメッセージを表示する.
   */
  private void showThrowable(@NonNull Throwable throwable) {
    showMessage(throwable.getMessage());
  }

  /**
   * {@link TextView} にメッセージを表示する.
   */
  private void showMessage(@NonNull String message) {
    textView.setText(message);
  }

  /**
   * データを保存.
   */
  private void saveShareDataModel(@NonNull ShareDataModel shareDataModel) {
    ShareDataModelManager.add(shareDataModel);
  }

  @Override
  protected void onDestroy() {
    compositeDisposable.clear();
    super.onDestroy();
  }

  /**
   * {@link LauncherActivity}を開く.
   * 失敗するとメッセージを表示するだけ.
   */
  private void openLauncher(@NonNull ShareDataModel shareDataModel) {
    if (shareDataModel.openLuncherActivity(this)) {
      finish();
      return;
    }
    showMessage("おや？何かがおかしいです\n" + shareDataModel.toJson());
  }
}
