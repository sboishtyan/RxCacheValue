package ru.sboishtyan.rx_cache_value;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

abstract class SingleCacheValueImpl<KEY, VALUE> extends CacheValueImpl<KEY, Single<VALUE>> implements SingleCacheValue<KEY, VALUE> {

    SingleCacheValueImpl(Fetcher<KEY, Single<VALUE>> fetcher, Cache<KEY, Single<VALUE>> cache) {
        super(fetcher, cache);
    }

    @Override
    public final void get(KEY key, @NonNull SingleObserver<VALUE> observer) {
        getInternal(key).subscribe(observer);
    }

    @Override
    public void fetch(KEY key, SingleObserver<VALUE> observer) {
        fetchOrExecuting(key).subscribe(observer);
    }

    @Override
    protected Completable toCompletable(Single<VALUE> value) {
        return value.toCompletable();
    }
}
