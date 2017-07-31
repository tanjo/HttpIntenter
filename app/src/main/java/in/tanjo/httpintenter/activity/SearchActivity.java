package in.tanjo.httpintenter.activity;

import com.atilika.kuromoji.ipadic.Tokenizer;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.SearchAdapter;
import in.tanjo.httpintenter.model.ShareDataModel;
import in.tanjo.httpintenter.model.SmallToken;
import in.tanjo.httpintenter.util.GsonUtils;
import in.tanjo.httpintenter.util.StringUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;


public class SearchActivity extends AppCompatActivity {

  @BindView(R.id.activity_search_recyclerview)
  RecyclerView recyclerView;

  @BindView(R.id.activity_search_progressbar_relativelayout)
  RelativeLayout progressBarView;

  private SearchAdapter adapter = new SearchAdapter();

  private BehaviorSubject<ShareDataModel> shareDataModel = BehaviorSubject.create();

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  private BehaviorSubject<Intent> intent = BehaviorSubject.create();

  private BehaviorSubject<Set<SmallToken>> tokens = BehaviorSubject.create();

  private PublishSubject<Integer> itemClicks = PublishSubject.create();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    ButterKnife.bind(this);
    recyclerView.setAdapter(adapter);
    DividerItemDecoration dividerItemDecoration =
        new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(this).getOrientation());
    recyclerView.addItemDecoration(dividerItemDecoration);
    adapter.setListener(this::onItemClick);
    subscribe();
    intent.onNext(getIntent());
  }

  private void subscribe() {
    // Item Click 時に遷移する
    compositeDisposable.add(Observable
        .combineLatest(tokens.map(ArrayList<SmallToken>::new), itemClicks, List::get)
        .map(this::createIntent)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::startActivity, Throwable::printStackTrace)
    );

    // Adapter に Token をセット
    compositeDisposable.add(tokens
        .map(ArrayList::new)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(i -> progressBarView.setVisibility(View.GONE))
        .subscribe(adapter::setTokens, throwable -> adapter.clearTokens())
    );

    // Token を作成
    compositeDisposable.add(shareDataModel
        .subscribeOn(new NewThreadScheduler())
        .map(this::createTokens)
        .subscribe(tokens::onNext, tokens::onError)
    );

    // Intent から ShareDataModel を取得
    compositeDisposable.add(intent
        .doOnNext(i -> progressBarView.setVisibility(View.VISIBLE))
        .subscribeOn(new NewThreadScheduler())
        .map(i -> i.getStringExtra(ShareDataModel.EXTRA_SHARE_DATA_MODEL))
        .map(s -> GsonUtils.getGson().fromJson(s, ShareDataModel.class))
        .subscribe(shareDataModel::onNext, shareDataModel::onError, shareDataModel::onComplete)
    );
  }

  @Override
  protected void onDestroy() {
    compositeDisposable.clear();
    super.onDestroy();
  }

  private void onItemClick(int position, SmallToken token) {
    itemClicks.onNext(position);
  }

  private Set<SmallToken> createTokens(ShareDataModel shareDataModel) {
    List<SmallToken> keywords = new ArrayList<>();
    Tokenizer tokenizer = new Tokenizer();
    List<String> strings = Arrays.asList(
        StringUtils.nullToEmpty(shareDataModel.title),
        StringUtils.nullToEmpty(shareDataModel.text),
        StringUtils.nullToEmpty(shareDataModel.subject)
    );
    for (String s : strings) {
      if (StringUtils.isNullOrEmpty(s)) {
        continue;
      }
      keywords.addAll(Observable
          .fromIterable(tokenizer.tokenize(s))
          .map(SmallToken::new)
          .toList()
          .blockingGet());
    }
    keywords.add(new SmallToken(StringUtils.nullToEmpty(shareDataModel.url), "URL"));

    return new HashSet<>(Observable.fromIterable(keywords)
        .filter(smallToken -> !StringUtils.isNullOrEmpty(smallToken.surface))
        .filter(smallToken -> !smallToken.surface.equals(" "))
        .toList()
        .blockingGet());
  }

  private Intent createIntent(SmallToken token) {
    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
    intent.putExtra(SearchManager.QUERY, token.surface);
    return intent;
  }
}
