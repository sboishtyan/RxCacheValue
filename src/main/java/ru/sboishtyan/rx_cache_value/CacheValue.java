package ru.sboishtyan.rx_cache_value;


import io.reactivex.Completable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import javax.annotation.Nullable;

interface CacheValue<KEY, VALUE> {

    /**
     * delete all cached values synchronously
     */
    void invalidateAll();

    /**
     * Prefetch value and put in cache, oldValue will be replaced
     *
     * @param key for fetch and put in cache
     * @return Disposable for unsubscribed from prefetch
     */
    @NonNull
    Disposable prefetch(KEY key);

    /**
     * Prefetch value and put in cache if cache by key empty
     *
     * @param key for fetch and put in cache
     * @return Disposable for unsubscribed from prefetch
     */
    @NonNull
    Disposable prefetchIfEmpty(KEY key);


    /**
     * For prefetch value with {@link Completable}
     *
     * @param key for fetch and put in cache
     * @return Completable that executes lazily in rx chain
     */
    @NonNull
    Completable prefetchAsCompletable(KEY key);

    /**
     * For prefetch value with {@link Completable} if cache by key empty
     *
     * @param key for fetch and put in cache
     * @return Completable that executes lazily in rx chain
     */
    @NonNull
    Completable prefetchIfEmptyAsCompletable(KEY key);

    /**
     * synchronously invalidate cache and return old value if exist
     *
     * @param key invalidate cache value by cacheKey
     * @return oldCacheValue can be null
     */
    @Nullable
    VALUE invalidate(KEY key);

    /**
     * Lazy value that can fetch when u want
     *
     * @param key for get
     * @return lazy value that exist or not
     */
    @NonNull
    VALUE lazy(KEY key);
}
