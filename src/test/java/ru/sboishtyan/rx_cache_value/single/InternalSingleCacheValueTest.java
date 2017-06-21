package ru.sboishtyan.rx_cache_value.single;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.sboishtyan.rx_cache_value.Fetcher;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class InternalSingleCacheValueTest {
    private final Fetcher<Integer, Single<String>> fetcher = Mockito.mock(Fetcher.class);
    private final TestInternalSingleCacheValue cacheValue = new TestInternalSingleCacheValue(fetcher);

    @Test
    public void given_empty_cache_and_empty_executing_when_get_then_getValue_internal() throws Exception {
        int cacheKey = 0;
        when(fetcher.fetch(cacheKey)).thenReturn(getValue(cacheKey));

        cacheValue.get(cacheKey, new TestObserver<>());

        verify(fetcher).fetch(cacheKey);
    }

    @Test
    public void given_not_empty_cache_when_get_then_return_cache_value() throws Exception {
        int cacheKey = 0;
        Single<String> value = getValue(cacheKey);
        cacheValue.cache.put(cacheKey, value);

        TestObserver<String> observer = new TestObserver<>();
        cacheValue.get(cacheKey, observer);

        observer.assertValue("0");
    }

    @Test
    public void given_empty_cache_and_not_empty_executing_when_get_then_return_executing_value() throws Exception {
        int cacheKey = 0;
        Single<String> value = getValue(cacheKey);
        cacheValue.executing.put(cacheKey, value);

        TestObserver<String> observer = new TestObserver<>();
        cacheValue.get(cacheKey, observer);

        observer.assertValue("0");
    }

    private Single<String> getValue(int cacheKey) {
        return Single.just(String.valueOf(cacheKey));
    }
}
