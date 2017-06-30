package ru.sboishtyan.rx_cache_value;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class MapCacheContainer<KEY, VALUE> implements ReadWriteCacheContainer<KEY, VALUE> {

    private final Map<KEY, VALUE> cached;
    private final Map<KEY, VALUE> executing;

    public MapCacheContainer(Map<KEY, VALUE> cached, Map<KEY, VALUE> executing) {
        this.cached = cached;
        this.executing = executing;
    }

    public MapCacheContainer() {
        this(new HashMap<>(), new HashMap<>());
    }

    @Nullable
    @Override
    public VALUE getCached(KEY key) {
        return cached.get(key);
    }

    @Nullable
    @Override
    public VALUE getExecuting(KEY key) {
        return executing.get(key);
    }

    @Override
    public void invalidateAll() {
        cached.clear();
    }

    @Nullable
    @Override
    public VALUE invalidate(KEY key) {
        return cached.remove(key);
    }

    @Override
    public void setExecuting(KEY key, VALUE value) {
        executing.put(key, value);
    }

    @Override
    public void setCached(KEY key, VALUE value) {
        cached.put(key, value);
    }
}
