package ru.sboishtyan.rx_cache_value;

import javax.annotation.Nullable;

final class CacheImpl<KEY, VALUE> implements Cache<KEY, VALUE> {

    private final ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer;
    private final InternalFetchCacheAction<KEY, VALUE> fetchCacheAction;

    CacheImpl(ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer, InternalFetchCacheAction<KEY, VALUE> fetchCacheAction) {
        this.readWriteCacheContainer = readWriteCacheContainer;
        this.fetchCacheAction = fetchCacheAction;
    }

    @Nullable
    @Override
    public VALUE getCached(KEY key) {
        return readWriteCacheContainer.getCached(key);
    }

    @Nullable
    @Override
    public VALUE getExecuting(KEY key) {
        return readWriteCacheContainer.getExecuting(key);
    }

    @Override
    public void invalidateAll() {
        readWriteCacheContainer.invalidateAll();
    }

    @Nullable
    @Override
    public VALUE invalidate(KEY cacheKey) {
        return readWriteCacheContainer.invalidate(cacheKey);
    }

    @Override
    public VALUE applyCacheActions(KEY cacheKey, VALUE value) {
        return fetchCacheAction.apply(cacheKey, value, readWriteCacheContainer);
    }
}
