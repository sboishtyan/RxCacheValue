package ru.sboishtyan.rx_cache_value;

interface FetchCacheAction<KEY, VALUE> {

    VALUE applyCacheActions(KEY cacheKey, VALUE value);
}
