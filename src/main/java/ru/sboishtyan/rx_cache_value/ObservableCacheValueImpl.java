package ru.sboishtyan.rx_cache_value;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;

abstract class ObservableCacheValueImpl<KEY, VALUE> extends CacheValueImpl<KEY, Observable<VALUE>> implements ObservableCacheValue<KEY, VALUE> {

    ObservableCacheValueImpl(Fetcher<KEY, Observable<VALUE>> fetcher, Cache<KEY, Observable<VALUE>> cache) {
        super(fetcher, cache);
    }

    @Override
    public final void get(KEY key, @NonNull Observer<VALUE> observer) {
        getInternal(key).subscribe(observer);
    }

    @Override
    public void fetch(KEY cacheKey, Observer<VALUE> observer) {
        fetchOrExecuting(cacheKey).subscribe(observer);
    }

    @Override
    protected Completable toCompletable(Observable<VALUE> value) {
        return value.ignoreElements();
    }
}
