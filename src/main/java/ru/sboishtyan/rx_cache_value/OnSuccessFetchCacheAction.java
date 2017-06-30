package ru.sboishtyan.rx_cache_value;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

final class OnSuccessFetchCacheAction<KEY, VALUE, T> implements Action, Consumer<T> {

    private final ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer;
    private final KEY key;

    OnSuccessFetchCacheAction(ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer, KEY key) {
        this.readWriteCacheContainer = readWriteCacheContainer;
        this.key = key;
    }

    @Override
    public void run() throws Exception {
        readWriteCacheContainer.setCached(key, readWriteCacheContainer.invalidate(key));
    }

    @Override
    public void accept(T value) throws Exception {
        readWriteCacheContainer.setCached(key, readWriteCacheContainer.invalidate(key));
    }
}
