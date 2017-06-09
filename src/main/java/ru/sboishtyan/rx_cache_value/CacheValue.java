package ru.sboishtyan.rx_cache_value;


import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import javax.annotation.Nullable;

/**
 * Interface for wrapping interactors and caching their first execution result
 */
public interface CacheValue<KEY, VALUE> {

    /**
     * Execute interactor or get value from cache if exist
     *
     * @param key for get value
     * @param observer get value in {@link SingleObserver#onSuccess(VALUE)}
     * @return Disposable for unsubscribed if need
     */
    void get(KEY key, @NonNull SingleObserver<VALUE> observer);

    /**
     * Prefetch value in cache or throws {@link IllegalStateException} if cache not empty
     *
     * @param key for prefetch value in cache
     * @return Disposable for unsubscribed from prefetch
     * @throws IllegalStateException if cache not empty,
     * in that way use {@link #prefetchForce} or combine @{link #prefetch} with {@link #invalidateCache(KEY)}
     */
    Disposable prefetch(KEY key) throws IllegalStateException;

    /**
     * Prefetch value in cache, if cache not empty oldValue will be replaced
     *
     * @param cacheKey for prefetchForce value in cache
     * @return Disposable for unsubscribed from prefetchForce
     */
    Disposable prefetchForce(KEY cacheKey);

    /**
     * Prefetch value in cache if cache empty
     *
     * @param cacheKey for prefetchForce value in cache
     * @return Disposable for unsubscribed from prefetchForce, return null when not empty
     */
    @Nullable
    Disposable prefetchIfEmpty(KEY cacheKey);

    /**
     * Synchroniously invalidate cache and return old value if exist
     *
     * @param cacheKey invalidate cache value by cacheKey
     * @return oldCacheValue can be null
     */
    @Nullable
    Single<VALUE> invalidateCache(KEY cacheKey);

    /**
     * For prefetch value with {@link Completable} chain of calss
     *
     * @param cacheKey for save value in cache
     * @return Completable that executes lazily or throw {@link IllegalStateException} in {@link io.reactivex.CompletableObserver#onError(Throwable)}
     */
    @NonNull
    Completable prefetchAsCompletable(KEY cacheKey);

    /**
     * Safe variant of {@link #prefetchAsCompletable}
     *
     * @param cacheKey for save value in cache
     * @return Completable that executes lazily
     */
    @NonNull
    Completable prefetchForceAsCompletable(KEY cacheKey);

    /**
     * Single that executes lazily in  {@link Single} chain of calls.
     * Works like {@link #get(Object, SingleObserver)}
     *
     * @param cacheKey for get from cache or save to cache after execute
     * @return single that u can chain
     */
    @NonNull
    Single<VALUE> asSingle(KEY cacheKey);

    /**
     * Map exist cacheValue by cacheKey or throws {@link IllegalStateException} if cache empty
     *
     * @param key for find in cache
     * @param function that maps cache value into new value
     * @throws IllegalStateException if cache empty
     */
    void map(KEY key, @NonNull Function<VALUE, VALUE> function) throws IllegalStateException;

    /**
     * Works like {@link #map(Object, Function)} but don't throws {@link IllegalStateException} if cache empty
     *  @param cacheKey for find in cache
     * @param function that maps cache value into new value
     */
    void mapSafe(KEY cacheKey, @NonNull Function<VALUE, VALUE> function);

}
