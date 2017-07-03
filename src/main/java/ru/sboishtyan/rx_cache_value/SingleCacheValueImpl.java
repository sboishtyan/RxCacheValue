package ru.sboishtyan.rx_cache_value;

import io.reactivex.Completable;
import io.reactivex.Single;

import javax.annotation.Nonnull;

final class SingleCacheValueImpl<KEY, VALUE> extends CacheValueImpl<KEY, Single<VALUE>> {

    public SingleCacheValueImpl(Fetcher<KEY, Single<VALUE>> fetcher, Cache<KEY, Single<VALUE>> cache) {
        super(fetcher, cache);
    }

    @Override
    protected Completable toCompletable(@Nonnull Single<VALUE> value) {
        return value.toCompletable();
    }
}
