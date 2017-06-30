package ru.sboishtyan.rx_cache_value;

import javax.annotation.Nullable;

interface ReadCacheContainer<KEY, VALUE> {

    @Nullable
    VALUE getCached(KEY key);

    @Nullable
    VALUE getExecuting(KEY key);

    void invalidateAll();

    @Nullable
    VALUE invalidate(KEY cacheKey);
}
