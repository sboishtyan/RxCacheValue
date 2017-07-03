package ru.sboishtyan.rx_cache_value;

import io.reactivex.Single;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.spy;

public class BaseSingleCacheValueTest {

    private final Fetcher<Integer, Single<String>> fetcher = new TestFetcher();
    private final Map<Integer, Single<String>> cacheSpy = spy(new HashMap<>());

    @Test
    public void when_execute_two_times_then_always_get_the_same_result() throws Exception {

    }
}
