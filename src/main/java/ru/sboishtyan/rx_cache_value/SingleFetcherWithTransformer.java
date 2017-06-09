package ru.sboishtyan.rx_cache_value;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;

import javax.annotation.Nonnull;

public final class SingleFetcherWithTransformer<KEY, VALUE> implements Fetcher<KEY, Single<VALUE>> {
    @Nonnull
    private final Fetcher<KEY, Single<VALUE>> delegate;
    @Nonnull
    private final SingleTransformer<VALUE, VALUE> transformer;

    public SingleFetcherWithTransformer(@Nonnull Fetcher<KEY, Single<VALUE>> delegate, @Nonnull SingleTransformer<VALUE, VALUE> transformer) {
        this.delegate = delegate;
        this.transformer = transformer;
    }

    @Override
    public Single<VALUE> fetch(KEY key) {
        return delegate.fetch(key).compose(transformer);
    }
}
