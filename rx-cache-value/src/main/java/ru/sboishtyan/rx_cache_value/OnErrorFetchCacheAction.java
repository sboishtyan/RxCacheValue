package ru.sboishtyan.rx_cache_value;

import io.reactivex.functions.Consumer;

final class OnErrorFetchCacheAction<KEY> implements Consumer<Throwable> {

    private final ReadWriteCacheContainer<KEY, ?> readWriteCacheContainer;
    private final KEY key;

    OnErrorFetchCacheAction(ReadWriteCacheContainer<KEY, ?> readWriteCacheContainer, KEY key) {
        this.readWriteCacheContainer = readWriteCacheContainer;
        this.key = key;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        readWriteCacheContainer.setExecuting(key, null);
    }
}
