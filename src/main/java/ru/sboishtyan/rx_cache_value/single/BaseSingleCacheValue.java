package ru.sboishtyan.rx_cache_value.single;


import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.sboishtyan.rx_cache_value.Fetcher;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BaseSingleCacheValue<REQUEST_VALUES, RESULT> extends InternalSingleCacheValue<REQUEST_VALUES, RESULT> {

    @NonNull
    private final Map<REQUEST_VALUES, Single<RESULT>> cache;
    @NonNull
    private final Map<REQUEST_VALUES, Single<RESULT>> executing = new HashMap<>();

    public BaseSingleCacheValue(@NonNull Fetcher<REQUEST_VALUES, Single<RESULT>> getValue) {
        this(getValue, new HashMap<>());
    }

    public BaseSingleCacheValue(@NonNull Fetcher<REQUEST_VALUES, Single<RESULT>> getValue, @NonNull Map<REQUEST_VALUES, Single<RESULT>> cache) {
        super(getValue);
        this.cache = cache;
    }

    @Override
    @Nullable
    public final Single<RESULT> invalidateCache(REQUEST_VALUES cacheKey) {
        return cache.remove(cacheKey);
    }

    @Override
    protected final Consumer<? super RESULT> putInCacheAction(final REQUEST_VALUES cacheKey) {
        return (Consumer<RESULT>) result -> cache.put(cacheKey, Single.just(result));
    }

    @Override
    protected final Single<RESULT> getCacheValue(REQUEST_VALUES cacheKey) {
        return cache.get(cacheKey);
    }

    @Nullable
    @Override
    protected final Single<RESULT> getExecuting(REQUEST_VALUES cacheKey) {
        return executing.get(cacheKey);
    }

    @Override
    protected final Action clearExecutingAction(final REQUEST_VALUES cacheKey) {
        return new Action() {
            @Override
            public void run() {
                executing.remove(cacheKey);
            }
        };
    }

    @Override
    protected final void setExecuting(REQUEST_VALUES cacheKey, Single<RESULT> executing) {
        this.executing.put(cacheKey, executing);
    }
}
