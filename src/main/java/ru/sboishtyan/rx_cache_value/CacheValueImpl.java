package ru.sboishtyan.rx_cache_value;

import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static io.reactivex.internal.functions.Functions.emptyConsumer;

abstract class CacheValueImpl<KEY, VALUE> implements CacheValue<KEY, VALUE> {

    private final Fetcher<KEY, VALUE> fetcher;
    private final Cache<KEY, VALUE> cache;

    CacheValueImpl(Fetcher<KEY, VALUE> fetcher, Cache<KEY, VALUE> cache) {
        this.fetcher = fetcher;
        this.cache = cache;
    }

    public final void invalidateAll() {
        cache.invalidateAll();
    }

    @NonNull
    @Override
    public final Disposable prefetch(KEY cacheKey) {
        return prefetchAsCompletable(cacheKey).subscribe(Functions.EMPTY_ACTION, emptyConsumer());
    }

    @NonNull
    @Override
    public final Disposable prefetchIfEmpty(KEY cacheKey) {
        return prefetchIfEmptyAsCompletable(cacheKey).subscribe(Functions.EMPTY_ACTION, emptyConsumer());
    }

    @Override
    @Nonnull
    public final Completable prefetchAsCompletable(KEY cacheKey) {
        return toCompletable(fetchOrExecuting(cacheKey));
    }

    @Override
    @Nonnull
    public final Completable prefetchIfEmptyAsCompletable(KEY cacheKey) {
        if (cache.getExecuting(cacheKey) == null && cache.getCached(cacheKey) == null) {
            return toCompletable(fetchInternal(cacheKey));
        } else {
            return Completable.complete();
        }
    }

    @Nullable
    public final VALUE invalidate(KEY cacheKey) {
        return cache.invalidate(cacheKey);
    }

    @Nonnull
    @Override
    public final VALUE get(KEY cacheKey) {
        return getInternal(cacheKey);
    }

    @Nonnull
    @Override
    public final VALUE fetch(KEY key) {
        return fetchOrExecuting(key);
    }

    protected abstract Completable toCompletable(VALUE value);

    @Nonnull
    private VALUE fetchOrExecuting(KEY cacheKey) {
        VALUE executing = cache.getExecuting(cacheKey);
        if (executing != null) {
            return executing;
        } else {
            return fetchInternal(cacheKey);
        }
    }

    @Nonnull
    private VALUE getInternal(KEY cacheKey) {
        VALUE executing = cache.getExecuting(cacheKey);
        if (executing != null) {
            return executing;
        } else {
            VALUE cached = cache.getCached(cacheKey);
            if (cached != null) {
                return cached;
            }
            return fetchInternal(cacheKey);
        }
    }

    @NonNull
    private VALUE fetchInternal(KEY cacheKey) {
        return cache.applyCacheActions(cacheKey, fetcher.fetch(cacheKey));
    }
}
