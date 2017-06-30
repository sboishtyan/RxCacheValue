package ru.sboishtyan.rx_cache_value;


import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

/**
 * Interface for wrapping interactors and caching their first execution result
 */
public interface SingleCacheValue<KEY, VALUE> extends CacheValue<KEY, Single<VALUE>> {

    /**
     * Get value from cache or fetch and put in cache
     *
     * @param key      for get value
     * @param observer get value in {@link SingleObserver#onSuccess(VALUE)}
     */
    void get(KEY key, @NonNull SingleObserver<VALUE> observer);

    /**
     * Fetch values and put in cache
     *
     * @param key      for fetch values
     * @param observer get values in {@link SingleObserver#onSuccess(VALUE)}
     */
    void fetch(KEY key, @NonNull SingleObserver<VALUE> observer);
}
