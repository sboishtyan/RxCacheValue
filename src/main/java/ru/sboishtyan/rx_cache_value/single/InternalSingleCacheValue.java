package ru.sboishtyan.rx_cache_value.single;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import ru.sboishtyan.rx_cache_value.CacheValue;
import ru.sboishtyan.rx_cache_value.Fetcher;

import javax.annotation.Nullable;

import static io.reactivex.internal.functions.Functions.emptyConsumer;

abstract class InternalSingleCacheValue<KEY, VALUE> implements CacheValue<KEY, VALUE> {

    private final Fetcher<KEY, Single<VALUE>> getValue;

    InternalSingleCacheValue(Fetcher<KEY, Single<VALUE>> getValue) {
        this.getValue = getValue;
    }

    @Override
    public final void get(KEY key, @NonNull SingleObserver<VALUE> observer) {
        executeInternal(key).subscribe(observer);
    }

    @Override
    public final Disposable prefetch(KEY key) throws IllegalStateException {
        Single<VALUE> cacheValue = getCacheValue(key);
        if (cacheValue != null) {
            throw new IllegalStateException("can't prefetch when cache not empty");
        } else {
            return prefetchForce(key);
        }
    }

    @Override
    public Disposable prefetchForce(KEY cacheKey) {
        Single<VALUE> executing = getExecuting(cacheKey);
        if (executing != null) {
            return executing.subscribe(emptyConsumer(), emptyConsumer());
        } else {
            return getValueInternal(cacheKey).subscribe(emptyConsumer(), emptyConsumer());
        }
    }

    @Nullable
    @Override
    public final Disposable prefetchIfEmpty(KEY cacheKey) {
        if (getExecuting(cacheKey) == null && getCacheValue(cacheKey) == null) {
            return prefetchForce(cacheKey);
        } else {
            return null;
        }
    }

    @NonNull
    @Override
    public final Completable prefetchAsCompletable(KEY cacheKey) {
        Single<VALUE> cacheValue = getCacheValue(cacheKey);
        if (cacheValue != null) {
            return Completable.error(new IllegalStateException("can't prefetch when cache not empty"));
        } else {
            return prefetchForceAsCompletable(cacheKey);
        }
    }

    @NonNull
    @Override
    public final Completable prefetchForceAsCompletable(KEY cacheKey) {
        Single<VALUE> executing = getExecuting(cacheKey);
        if (executing != null) {
            return executing.toCompletable();
        } else {
            return getValueInternal(cacheKey).toCompletable();
        }
    }

    @NonNull
    @Override
    public final Single<VALUE> asSingle(KEY cacheKey) {
        return executeInternal(cacheKey);
    }

    @Override
    public void map(KEY key, @NonNull Function<VALUE, VALUE> function) {
        Single<VALUE> cacheValue = getCacheValue(key);
        if (cacheValue != null) {
            cacheValue.map(function).doOnSuccess(getOnSuccessCacheAction(key)).subscribe(emptyConsumer(), emptyConsumer());
        } else {
            throw new IllegalStateException("can't map when cache empty");
        }
    }

    @Override
    public void mapSafe(KEY cacheKey, @NonNull Function<VALUE, VALUE> function) throws IllegalStateException {
        Single<VALUE> cacheValue = getCacheValue(cacheKey);
        if (cacheValue != null) {
            cacheValue.map(function).doOnSuccess(getOnSuccessCacheAction(cacheKey)).subscribe(emptyConsumer(), emptyConsumer());
        }
    }

    protected abstract Consumer<? super VALUE> getOnSuccessCacheAction(KEY cacheKey);

    @Nullable
    protected abstract Single<VALUE> getCacheValue(KEY cacheKey);

    @Nullable
    protected abstract Single<VALUE> getExecuting(KEY cacheKey);

    protected abstract Action getAfterTerminateCacheAction(KEY cacheKey);

    protected abstract void setExecuting(KEY cacheKey, Single<VALUE> executing);

    private Single<VALUE> executeInternal(KEY cacheKey) {
        Single<VALUE> value = getCacheValue(cacheKey);
        if (value != null) {
            return value;
        } else {
            Single<VALUE> executing = getExecuting(cacheKey);
            if (executing != null) {
                return executing;
            } else {
                return getValueInternal(cacheKey);
            }
        }
    }

    @NonNull
    private Single<VALUE> getValueInternal(KEY cacheKey) {
        PublishSubject<VALUE> subject = PublishSubject.create();
        Single<VALUE> executing = subject.firstOrError();
        setExecuting(cacheKey, executing);
        return getValue.fetch(cacheKey)
                .doAfterTerminate(getAfterTerminateCacheAction(cacheKey))
                .doOnSuccess(getOnSuccessCacheAction(cacheKey))
                .doOnSuccess(subject::onNext).doOnError(subject::onError);
    }
}
