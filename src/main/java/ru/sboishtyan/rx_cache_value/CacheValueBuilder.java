package ru.sboishtyan.rx_cache_value;

import io.reactivex.Observable;
import io.reactivex.Single;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class CacheValueBuilder<KEY, VALUE> {

    @Nonnull
    final Fetcher<KEY, VALUE> fetcher;
    @Nullable
    private ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer;

    private CacheValueBuilder(@Nonnull Fetcher<KEY, VALUE> fetcher) {
        this.fetcher = fetcher;
    }

    public static <KEY, VALUE> CacheValueBuilder<KEY, Single<VALUE>> single(@Nonnull Fetcher<KEY, Single<VALUE>> fetcher) {
        return new SingleCacheValueBuilder<>(fetcher);
    }

    public static <KEY, VALUE> CacheValueBuilder<KEY, Observable<VALUE>> observable(@Nonnull Fetcher<KEY, Observable<VALUE>> fetcher) {
        return new ObservableCacheValueBuilder<>(fetcher);
    }

    @Nonnull
    public CacheValueBuilder<KEY, VALUE> cacheContainer(ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer) {
        this.readWriteCacheContainer = readWriteCacheContainer;
        return this;
    }

    @Nonnull
    public abstract CacheValue<KEY, VALUE> build();

    @Nonnull
    final ReadWriteCacheContainer<KEY, VALUE> getCacheContainerOrDefault() {
        if (readWriteCacheContainer != null) {
            return readWriteCacheContainer;
        }
        return new MapCacheContainer<>();
    }

    private static class SingleCacheValueBuilder<KEY, VALUE> extends CacheValueBuilder<KEY, Single<VALUE>> {

        private SingleCacheValueBuilder(@Nonnull Fetcher<KEY, Single<VALUE>> fetcher) {
            super(fetcher);
        }

        @Nonnull
        @Override
        public CacheValue<KEY, Single<VALUE>> build() {
            return new SingleCacheValueImpl<>(fetcher, new CacheImpl<>(getCacheContainerOrDefault(), new SingleFetchCacheAction<>()));
        }
    }

    private static class ObservableCacheValueBuilder<KEY, VALUE> extends CacheValueBuilder<KEY, Observable<VALUE>> {

        private ObservableCacheValueBuilder(@Nonnull Fetcher<KEY, Observable<VALUE>> fetcher) {
            super(fetcher);
        }

        @Nonnull
        @Override
        public CacheValue<KEY, Observable<VALUE>> build() {
            return new ObservableCacheValueImpl<>(fetcher, new CacheImpl<>(getCacheContainerOrDefault(), new ObservableFetchCacheAction<>()));
        }
    }
}
