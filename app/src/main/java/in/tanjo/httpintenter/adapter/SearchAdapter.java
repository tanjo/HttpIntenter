package in.tanjo.httpintenter.adapter;

import com.atilika.kuromoji.ipadic.Token;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.tanjo.httpintenter.R;
import in.tanjo.httpintenter.adapter.holder.SearchViewHolder;
import in.tanjo.httpintenter.model.SmallToken;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;


public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

  private List<SmallToken> tokens = new ArrayList<>();

  @Nullable
  private Listener listener;

  public void setTokens(@NonNull List<SmallToken> tokens) {
    this.tokens = Observable.fromIterable(tokens).toSortedList(SmallToken::compareTo).blockingGet();
    notifyDataSetChanged();
  }

  public void clearTokens() {
    this.tokens.clear();
    notifyDataSetChanged();
  }

  public void setListener(@Nullable Listener listener) {
    this.listener = listener;
  }

  @Override
  public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_search, parent, false);
    return new SearchViewHolder(view, this::onTokenClick);
  }

  @Override
  public void onBindViewHolder(SearchViewHolder holder, int position) {
    SmallToken token = tokens.get(position);
    if (holder == null) {
      return;
    }
    holder.bind(token, position);
  }

  @Override
  public int getItemCount() {
    return tokens.size();
  }

  private void onTokenClick(final int position) {
    Observable
        .combineLatest(Observable
            .just(position)
            .filter(i -> 0 <= i && i < tokens.size()), Observable.just(tokens), (i, tokens) -> tokens.get(i))
        .subscribe(token -> {
          if (listener != null) {
            listener.onItemClick(position, token);
          }
        }, Throwable::printStackTrace);
  }

  public interface Listener {

    void onItemClick(int position, SmallToken token);
  }
}
