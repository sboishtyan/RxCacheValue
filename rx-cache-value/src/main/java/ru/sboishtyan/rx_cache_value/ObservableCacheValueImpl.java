package ru.sboishtyan.rx_cache_value;

import io.reactivex.Completable;
import io.reactivex.Observable;

import javax.annotation.Nonnull;

final class ObservableCacheValueImpl<KEY, VALUE> extends CacheValueImpl<KEY, Observable<VALUE>> {

    ObservableCacheValueImpl(Fetcher<KEY, Observable<VALUE>> fetcher, Cache<KEY, Observable<VALUE>> cache) {
        super(fetcher, cache);
    }

    @Override
    protected Completable toCompletable(@Nonnull Observable<VALUE> value) {
        return value.ignoreElements();
    }
}
