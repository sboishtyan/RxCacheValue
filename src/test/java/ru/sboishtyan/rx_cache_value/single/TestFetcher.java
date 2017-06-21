package ru.sboishtyan.rx_cache_value.single;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.sboishtyan.rx_cache_value.Fetcher;

public class TestFetcher implements Fetcher<Integer, Single<String>> {

    private int i = 0;

    @Override
    public Single<String> fetch(Integer integer) {
        return Single.fromCallable(() -> {
            i += 1;
            return String.valueOf(integer + i);
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline());
    }

    public int subscribeCount() {
        return i;
    }
}
