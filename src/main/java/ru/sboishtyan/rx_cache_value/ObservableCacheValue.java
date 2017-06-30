package ru.sboishtyan.rx_cache_value;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

/**
 * Interface for wrapping interactors and caching their first execution result
 */
public interface ObservableCacheValue<KEY, VALUE> extends CacheValue<KEY, Observable<VALUE>> {

    /**
     * Get values from cache or fetch and put in cache
     *
     * @param key      for get values
     * @param observer get values in {@link Observer#onNext(VALUE)}
     */
    void get(KEY key, @NonNull Observer<VALUE> observer);

    /**
     * Fetch values and put in cache
     *
     * @param key      for fetch values
     * @param observer get values in {@link Observer#onNext(VALUE)}
     */
    void fetch(KEY key, @NonNull Observer<VALUE> observer);
}
