package me.zane.basicbus.api.bus;

import me.zane.basicbus.api.annotations.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @since 1.4.0
 */

public interface Bus<T> {

    Map<List<Class<?>>, List<Site>> map();

    /**
     * When an {@link Object} is subscribed any method annotated with {@link Listener} will be called
     * when an instance of the class {@link Listener#value} is posted with {@link Bus#post}.
     *
     * @param subscriber {@link Object} to be subscribed
     */
    default void subscribe(Object subscriber) {
        final Method[] ms = subscriber.getClass().getDeclaredMethods();
        final Map<List<Class<?>>, List<Site>> map = map();
        for (final Method m : ms) {
            boolean a = false;
            final Listener l = m.getAnnotation(Listener.class);
            if (l != null) {
                final Class<?>[] p = m.getParameterTypes();
                final int pl = p.length;
                final List<Class<?>> ecs = Arrays.asList(l.value());
                if (pl <= ecs.size()) {
                    for (int i = 0; i < pl; i++) {
                        if (!ecs.contains(p[i])) {
                            a = true;
                            break;
                        }
                    }
                    if (a) continue;
                    final Site cl = new Site(subscriber, m);
                    if (map.containsKey(ecs)) map.get(ecs).add(cl);
                    else map.put(ecs, new ArrayList<>(Collections.singletonList(cl)));
                }
            }
        }
    }

    /**
     * Once a subscriber has been unsubscribed any method annotated with {@link Listener} no longer
     * will be called on {@link Bus#post}
     *
     * @param subscriber Any {@link Object} that has been subscribed using {@link Bus#subscribe}.
     */
    default void unsubscribe(Object subscriber) {
        final Collection<List<Site>> cl = map().values();
        for (List<Site> cls : cl) cls.removeIf(c -> c.s == subscriber);
    }

    /**
     * @param event Any {@link T}, when {@code event} is posted it invokes all
     *              methods annotated with {@link Listener} and with either 0 or 1 parameter(s),
     *              if 1 parameter is present it must be of matching type as the class specified
     *              in {@link Listener#value}. Only if an instance of the method's containing
     *              class has been subscribed using {@link Bus#subscribe} will it be invoked.
     */
    default void post(T event) {
        final List<Site> cls = map().get(Collections.singletonList(event.getClass()));
        if (cls != null) {
            for (final Site cl : cls) {
                final Method m = cl.m;
                final Object sub = cl.s;

                try {
                    if (cl.nP) m.invoke(sub);
                    else m.invoke(sub, event);
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                }
            }
        }
    }
}
