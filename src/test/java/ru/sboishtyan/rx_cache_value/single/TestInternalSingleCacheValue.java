package ru.sboishtyan.rx_cache_value.single;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.sboishtyan.rx_cache_value.Fetcher;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class TestInternalSingleCacheValue extends InternalSingleCacheValue<Integer, String> {

    final Map<Integer, Single<String>> cache = new HashMap<>();
    final Map<Integer, Single<String>> executing = new HashMap<>();

    TestInternalSingleCacheValue(Fetcher<Integer, Single<String>> getValue) {
        super(getValue);
    }

    @Nullable
    @Override
    public Single<String> invalidateCache(Integer cacheKey) {
        return null;
    }

    @Override
    protected Consumer<? super String> putInCacheAction(Integer cacheKey) {
        return new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        };
    }

    @Nullable
    @Override
    protected Single<String> getCacheValue(Integer cacheKey) {
        return cache.get(cacheKey);
    }

    @Nullable
    @Override
    protected Single<String> getExecuting(Integer cacheKey) {
        return executing.get(cacheKey);
    }

    @Override
    protected Action clearExecutingAction(Integer cacheKey) {
        return new Action() {
            @Override
            public void run() throws Exception {

            }
        };
    }

    @Override
    protected void setExecuting(Integer cacheKey, Single<String> executing) {

    }
}
