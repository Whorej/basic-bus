package me.zane.basicbus.api.bus;

import me.zane.basicbus.api.annotations.Listener;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An asynchronous implementation of {@link Bus}.
 * @since 1.4.0
 */
public final class AsyncBus<T> implements Bus<T> {

    private final Map<List<Class<?>>, List<Site>> map = new ConcurrentHashMap<>();

    @Override
    public Map<List<Class<?>>, List<Site>> map() {
        return map;
    }

//    @Override
//    public void subscribe(Object subscriber) {
//        final Method[] ms = subscriber.getClass().getDeclaredMethods();
//        final Map<Class<?>, List<Site>> map = this.map;
//        for (final Method m : ms) {
//            final Listener l = m.getAnnotation(Listener.class);
//            if (l != null) {
//                final Class<?>[] ps = m.getParameterTypes();
//                final int pl = ps.length;
//                if (pl <= 1) {
//                    final Class<?> c = l.value();
//                    if (pl == 1 && c != ps[0]) continue;
//                    final Site cl = new Site(subscriber, m);
//                    if (map.containsKey(c)) map.get(c).add(cl);
//                    else map.put(c, new CopyOnWriteArrayList<>(Collections.singletonList(cl)));
//                }
//            }
//        }
//    }
}
