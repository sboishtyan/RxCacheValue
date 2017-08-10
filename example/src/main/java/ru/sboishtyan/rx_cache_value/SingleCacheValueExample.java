package ru.sboishtyan.rx_cache_value;

import io.reactivex.Single;

import javax.annotation.Nullable;

public class SingleCacheValueExample {
    public static void main(String[] args) {
        Fetcher<Object, Single<Object>> singleFetcher = key -> null;
        CacheValue<Object, Single<Object>> build = CacheValueBuilder
                .single(singleFetcher) //required
                .cacheContainer(new ReadWriteCacheContainer<Object, Single<Object>>() {
                    @Override
                    public void setExecuting(Object o, Single<Object> objectSingle) {

                    }

                    @Override
                    public void setCached(Object o, Single<Object> objectSingle) {

                    }

                    @Nullable
                    @Override
                    public Single<Object> getCached(Object o) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Single<Object> getExecuting(Object o) {
                        return null;
                    }

                    @Override
                    public void invalidateAll() {

                    }

                    @Nullable
                    @Override
                    public Single<Object> invalidate(Object cacheKey) {
                        return null;
                    }
                }) // optional
                .build();
    }
}
