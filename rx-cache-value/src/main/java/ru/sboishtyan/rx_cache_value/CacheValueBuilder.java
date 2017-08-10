package ru.sboishtyan.rx_cache_value;

import io.reactivex.Observable;
import io.reactivex.Single;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class CacheValueBuilder<KEY, VALUE> {

    private abstract static class InternalCacheValueBuilder<KEY, VALUE> extends CacheValueBuilder<KEY, VALUE> {
        @Nonnull
        final Fetcher<KEY, VALUE> fetcher;

        @Nullable
        private ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer;

        private InternalCacheValueBuilder(@Nonnull Fetcher<KEY, VALUE> fetcher) {
            this.fetcher = fetcher;
        }

        @Nonnull
        public final CacheValueBuilder<KEY, VALUE> cacheContainer(ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer) {
            this.readWriteCacheContainer = readWriteCacheContainer;
            return this;
        }

        @Nonnull
        private ReadWriteCacheContainer<KEY, VALUE> getCacheContainerOrDefault() {
            if (readWriteCacheContainer != null) {
                return readWriteCacheContainer;
            }
            return new MapCacheContainer<>();
        }

        @Nonnull
        public final CacheValue<KEY, VALUE> build() {
            return buildInternal(fetcher, getCacheContainerOrDefault());
        }

        abstract CacheValue<KEY, VALUE> buildInternal(Fetcher<KEY, VALUE> fetcher, ReadWriteCacheContainer<KEY, VALUE> cacheContainer);
    }


    public static <KEY, VALUE> CacheValueBuilder<KEY, Single<VALUE>> single(@Nonnull Fetcher<KEY, Single<VALUE>> fetcher) {
        return new SingleCacheValueBuilder<>(fetcher);
    }

    public static <KEY, VALUE> CacheValueBuilder<KEY, Observable<VALUE>> observable(@Nonnull Fetcher<KEY, Observable<VALUE>> fetcher) {
        return new ObservableCacheValueBuilder<>(fetcher);
    }

    @Nonnull
    public abstract CacheValue<KEY, VALUE> build();

    @Nonnull
    public abstract CacheValueBuilder<KEY, VALUE> cacheContainer(ReadWriteCacheContainer<KEY, VALUE> readWriteCacheContainer);

    private static class SingleCacheValueBuilder<KEY, VALUE> extends InternalCacheValueBuilder<KEY, Single<VALUE>> {

        private SingleCacheValueBuilder(@Nonnull Fetcher<KEY, Single<VALUE>> fetcher) {
            super(fetcher);
        }

        @Override
        CacheValue<KEY, Single<VALUE>> buildInternal(Fetcher<KEY, Single<VALUE>> fetcher, ReadWriteCacheContainer<KEY, Single<VALUE>> cacheContainer) {
            return new SingleCacheValueImpl<>(fetcher, new CacheImpl<>(cacheContainer, new SingleFetchCacheAction<>()));
        }
    }

    private static class ObservableCacheValueBuilder<KEY, VALUE> extends InternalCacheValueBuilder<KEY, Observable<VALUE>> {

        private ObservableCacheValueBuilder(@Nonnull Fetcher<KEY, Observable<VALUE>> fetcher) {
            super(fetcher);
        }

        @Override
        CacheValue<KEY, Observable<VALUE>> buildInternal(Fetcher<KEY, Observable<VALUE>> fetcher, ReadWriteCacheContainer<KEY, Observable<VALUE>> cacheContainer) {
            return new ObservableCacheValueImpl<>(fetcher, new CacheImpl<>(cacheContainer, new ObservableFetchCacheAction<>()));
        }
    }
}
