package me.zane.basicbus.api.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of {@link Bus}.
 * @since 1.4.0
 */
public final class BusImpl<T> implements Bus<T> {

    private final Map<Class<?>, List<Site>> map = new HashMap<>();

    @Override
    public Map<Class<?>, List<Site>> map() {
        return map;
    }
}
