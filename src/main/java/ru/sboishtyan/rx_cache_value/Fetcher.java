package ru.sboishtyan.rx_cache_value;

public interface Fetcher<KEY, VALUE> {
    VALUE fetch(KEY key);
}
