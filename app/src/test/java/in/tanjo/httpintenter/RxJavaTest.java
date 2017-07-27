package in.tanjo.httpintenter;

import com.google.common.base.Optional;

import org.junit.Test;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RxJavaTest {

  @Test
  public void nullTest() throws Exception {
    Single.just(Optional.absent())
        .subscribeOn(new TestScheduler())
        .observeOn(new TestScheduler())
        .map(Optional::get)
        .subscribe(object -> assertThat(object).isInstanceOf(Optional.class), throwable -> assertThat(throwable).isNotNull());
  }

  @Test
  public void arrayTest() throws Exception {
    Observable.fromIterable(Arrays.asList(1, 2, 3, 3))
        .subscribeOn(new TestScheduler())
        .observeOn(new TestScheduler())
        .map(String::valueOf)
        .toList()
        .subscribe(strings -> assertThat(strings).isNotNull().contains("1", "2", "3", "4"));
  }
}
