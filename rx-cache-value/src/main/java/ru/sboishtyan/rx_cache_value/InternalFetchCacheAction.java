package ru.sboishtyan.rx_cache_value;

interface InternalFetchCacheAction<KEY, VALUE> {

    VALUE apply(KEY key, VALUE value, ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer);
}
