package ru.sboishtyan.rx_cache_value;

import io.reactivex.Observable;

import javax.annotation.Nullable;

public class ObservableCacheValueExample {
    public static void main(String[] args) {
        Fetcher<Object, Observable<Object>> observableFetcher = key -> null;
        CacheValue<Object, Observable<Object>> value = CacheValueBuilder
                .observable(observableFetcher) //required
                .cacheContainer(new ReadWriteCacheContainer<Object, Observable<Object>>() {
                    @Override
                    public void setExecuting(Object o, Observable<Object> objectObservable) {

                    }

                    @Override
                    public void setCached(Object o, Observable<Object> objectObservable) {

                    }

                    @Nullable
                    @Override
                    public Observable<Object> getCached(Object o) {
                        return null;
                    }

                    @Nullable
                    @Override
                    public Observable<Object> getExecuting(Object o) {
                        return null;
                    }

                    @Override
                    public void invalidateAll() {

                    }

                    @Nullable
                    @Override
                    public Observable<Object> invalidate(Object cacheKey) {
                        return null;
                    }
                }) //optional
                .build();
    }
}
