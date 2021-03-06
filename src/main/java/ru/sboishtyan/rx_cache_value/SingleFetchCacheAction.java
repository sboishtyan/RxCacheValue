package ru.sboishtyan.rx_cache_value;

import io.reactivex.Single;

import javax.annotation.Nonnull;

final class SingleFetchCacheAction<KEY, VALUE> implements InternalFetchCacheAction<KEY, Single<VALUE>> {

    @Override
    public Single<VALUE> apply(KEY key, Single<VALUE> value, @Nonnull ReadWriteCacheContainer<KEY, Single<VALUE>> readWriteCacheContainer) {
        value = value.doOnError(new OnErrorFetchCacheAction<>(readWriteCacheContainer, key))
                .doOnSuccess(new OnSuccessFetchCacheAction<>(readWriteCacheContainer, key))
                .cache();
        readWriteCacheContainer.setExecuting(key, value);
        return value;
    }
}
