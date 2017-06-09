package ru.sboishtyan.rx_cache_value;


import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BaseCacheValue<REQUEST_VALUES, RESULT> extends InternalCacheValue<REQUEST_VALUES, RESULT> {

    @NonNull
    private final Map<REQUEST_VALUES, Single<RESULT>> cache;
    @NonNull
    private final Map<REQUEST_VALUES, Single<RESULT>> executing = new HashMap<>();

    public BaseCacheValue(@NonNull Fetcher<REQUEST_VALUES, Single<RESULT>> getValue) {
        this(getValue, new HashMap<>());
    }

    public BaseCacheValue(@NonNull Fetcher<REQUEST_VALUES, Single<RESULT>> getValue, @NonNull Map<REQUEST_VALUES, Single<RESULT>> cache) {
        super(getValue);
        this.cache = cache;
    }

    @Override
    @Nullable
    public Single<RESULT> invalidateCache(REQUEST_VALUES cacheKey) {
        return cache.remove(cacheKey);
    }

    @Override
    protected Consumer<? super RESULT> getOnSuccessCacheAction(final REQUEST_VALUES cacheKey) {
        return (Consumer<RESULT>) result -> cache.put(cacheKey, Single.just(result));
    }

    @Override
    protected Single<RESULT> getCacheValue(REQUEST_VALUES cacheKey) {
        return cache.get(cacheKey);
    }

    @Nullable
    @Override
    protected Single<RESULT> getExecuting(REQUEST_VALUES cacheKey) {
        return executing.get(cacheKey);
    }

    @Override
    protected Action getAfterTerminateCacheAction(final REQUEST_VALUES cacheKey) {
        return new Action() {
            @Override
            public void run() {
                executing.remove(cacheKey);
            }
        };
    }

    @Override
    protected void setExecuting(REQUEST_VALUES cacheKey, Single<RESULT> executing) {
        this.executing.put(cacheKey, executing);
    }
}
