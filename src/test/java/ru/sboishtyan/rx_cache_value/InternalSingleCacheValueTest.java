package ru.sboishtyan.rx_cache_value;

import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class InternalSingleCacheValueTest {

    private final Fetcher<Integer, Single<String>> fetcher = Mockito.mock(Fetcher.class);

    @Test
    public void given_empty_cache_and_empty_executing_when_get_then_getValue_internal() throws Exception {

    }

    @Test
    public void given_not_empty_cache_when_get_then_return_cache_value() throws Exception {

    }

    @Test
    public void given_empty_cache_and_not_empty_executing_when_get_then_return_executing_value() throws Exception {

    }
}
