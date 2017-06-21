package ru.sboishtyan.rx_cache_value.single;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import ru.sboishtyan.rx_cache_value.Fetcher;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.spy;

public class BaseSingleCacheValueTest {
    private final Fetcher<Integer, Single<String>> fetcher = new TestFetcher();
    private final Map<Integer, Single<String>> cacheSpy = spy(new HashMap<>());
    private final BaseSingleCacheValue<Integer, String> cacheValue = new BaseSingleCacheValue<>(fetcher, cacheSpy);

    @Test
    public void when_execute_two_times_then_always_get_the_same_result() throws Exception {
        TestObserver<String> observer1 = new TestObserver<>();
        TestObserver<String> observer2 = new TestObserver<>();
        cacheValue.get(0, observer1);
        cacheValue.get(0, observer2);
        observer1.await();
        observer2.await();
        observer1.assertValues("1");
        observer2.assertValues("1");
    }
}
