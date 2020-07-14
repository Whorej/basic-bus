package me.zane.basicbus.api.bus;

import me.zane.basicbus.api.annotations.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An asynchronous implementation of {@link Bus}.
 * @since 1.4.0
 */
public final class AsyncBus<T> implements Bus<T> {

    private final Map<Class<?>, CopyOnWriteArrayList<Site>> map = new ConcurrentHashMap<>();

    /**
     * When an {@link Object} is subscribed, any method annotated with {@link Listener} will be called
     * if an instance of the class {@link Listener#value} is posted with {@link AsyncBus#post}.
     *
     * @param subscriber {@link Object} to be subscribed
     */
    @Override
    public void subscribe(Object subscriber) {
        final Method[] ms = subscriber.getClass().getDeclaredMethods();
        final Map<Class<?>, CopyOnWriteArrayList<Site>> map = this.map;
        for (final Method m : ms) {
            final Listener l = m.getAnnotation(Listener.class);
            if (l != null) {
                final Class<?>[] p = m.getParameterTypes();
                final int pl = p.length;
                final Class<?> ecs = l.value();
                if (pl <= 1) {
                    if (pl == 1 && ecs != p[0]) continue;
                    final Site cl = new Site(subscriber, m);
                    if (map.containsKey(ecs)) map.get(ecs).add(cl);
                    else {
                        PLACEHOLDER[0] = cl;
                        map.put(ecs, new CopyOnWriteArrayList<>(PLACEHOLDER));
                    }
                }
            }
        }
    }

    /**
     * Once a subscriber has been unsubscribed any method annotated with {@link Listener} no longer
     * will be called on {@link AsyncBus#post}
     *
     * @param subscriber Any {@link Object} that has been subscribed using {@link AsyncBus#subscribe}.
     */
    @Override
    public void unsubscribe(Object subscriber) {
        final Collection<CopyOnWriteArrayList<Site>> cl = this.map.values();
        for (CopyOnWriteArrayList<Site> cls : cl) cls.removeIf(c -> c.s == subscriber);
    }

    /**
     * @param event Any {@link T}, when {@code event} is posted it invokes all
     *              methods annotated with {@link Listener} and with either 0 or 1 parameter(s),
     *              if 1 parameter is present it must be of matching type as the class specified
     *              in {@link Listener#value}. Only if an instance of the method's containing
     *              class has been subscribed using {@link AsyncBus#subscribe} will it be invoked.
     */
    @Override
    public void post(T event) {
        final CopyOnWriteArrayList<Site> cls = this.map.get(event.getClass());
        if (cls != null) {
            for (int i = 0, clsSize = cls.size(); i < clsSize; i++) {
                final Site cl = cls.get(i);
                final Method m = cl.m;
                final Object sub = cl.s;

                try {
                    if (cl.nP) m.invoke(sub);
                    else m.invoke(sub, event);
                } catch (IllegalAccessException | InvocationTargetException | IndexOutOfBoundsException ignored) {}
            }
        }
    }
}
