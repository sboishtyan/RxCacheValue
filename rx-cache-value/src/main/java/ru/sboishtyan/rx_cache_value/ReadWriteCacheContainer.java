package ru.sboishtyan.rx_cache_value;

interface ReadWriteCacheContainer<KEY, VALUE> extends ReadCacheContainer<KEY, VALUE> {

    void setExecuting(KEY key, VALUE value);

    void setCached(KEY key, VALUE value);
}
