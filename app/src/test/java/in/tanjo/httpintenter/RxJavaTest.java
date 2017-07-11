package in.tanjo.httpintenter;

import com.google.common.base.Optional;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RxJavaTest {

  @Test
  public void nullTest() throws Exception {
    Single.just(Optional.fromNullable(null))
        .subscribeOn(new TestScheduler())
        .observeOn(new TestScheduler())
        .subscribe(new Consumer<Object>() {
          @Override
          public void accept(@NonNull Object object) throws Exception {
            assertThat(object).isInstanceOf(Optional.class);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(@NonNull Throwable throwable) throws Exception {
            assertThat(throwable).isNotNull();
          }
        });
  }

  @Test
  public void arrayTest() throws Exception {
    Observable.fromIterable(Arrays.asList(1, 2, 3, 3))
        .subscribeOn(new TestScheduler())
        .observeOn(new TestScheduler())
        .map(new Function<Integer, String>() {
          @Override
          public String apply(@NonNull Integer integer) throws Exception {
            return String.valueOf(integer);
          }
        })
        .toList()
        .subscribe(new Consumer<List<String>>() {
          @Override
          public void accept(@NonNull List<String> strings) throws Exception {
            assertThat(strings).isNotNull().contains("1", "2", "3", "4");
          }
        });
  }
}
