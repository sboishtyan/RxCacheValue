package ru.sboishtyan.rx_cache_value;

interface Cache<KEY, VALUE> extends FetchCacheAction<KEY, VALUE>, ReadCacheContainer<KEY, VALUE> {

}
