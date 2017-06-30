package ru.sboishtyan.rx_cache_value;

import io.reactivex.Observable;

final class ObservableFetchCacheAction<KEY, VALUE> implements InternalFetchCacheAction<KEY, Observable<VALUE>> {

    @Override
    public Observable<VALUE> apply(KEY key, Observable<VALUE> value, ReadWriteCacheContainer<KEY, Observable<VALUE>> readWriteCacheContainer) {
        value = value.doOnError(new OnErrorFetchCacheAction<>(readWriteCacheContainer, key))
                .doOnComplete(new OnSuccessFetchCacheAction<>(readWriteCacheContainer, key))
                .cache();
        readWriteCacheContainer.setExecuting(key, value);
        return value;
    }
}
