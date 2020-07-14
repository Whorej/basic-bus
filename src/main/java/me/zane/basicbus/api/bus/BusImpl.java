package me.zane.basicbus.api.bus;

import me.zane.basicbus.api.annotations.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Default implementation of {@link Bus}.
 *
 * @since 1.4.0
 */
public final class BusImpl<T> implements Bus<T> {

    private final Map<Class<?>, List<Site>> map = new HashMap<>();

    /**
     * When an {@link Object} is subscribed, any method annotated with {@link Listener} will be called
     * if an instance of the class {@link Listener#value} is posted with {@link BusImpl#post}.
     * Note: Needs to be optimized is currently very slow in comparision to {@link BusImpl#post} and {@link BusImpl#unsubscribe}
     *
     * @param subscriber {@link Object} to be subscribed
     */
    @Override
    public void subscribe(Object subscriber) {
        final Method[] ms = subscriber.getClass().getDeclaredMethods();
        final Map<Class<?>, List<Site>> map = this.map;
        for (int i = 0, msLength = ms.length; i < msLength; i++) {
            final Method m = ms[i];
            final Listener l = m.getAnnotation(Listener.class);
            if (l != null) {
                final Class<?>[] p = m.getParameterTypes();
                final int pl = p.length;
                final Class<?> ecs = l.value();
                if (pl <= 1) {
                    // if method has parameter, make sure it is same type as Listener#value
                    if (pl == 1 && ecs != p[0]) continue;
                    final Site cl = new Site(subscriber, m);
                    // if the target event has had one of its subscribers subscribed before add to the existing ArrayList
                    if (map.containsKey(ecs)) map.get(ecs).add(cl);
                    // else create a new single-element ArrayList
                    else {
                        // avoid new array creation
                        PLACEHOLDER[0] = cl;
                        // in the JDK ArrayList(E[] array) is package-private is it must be called via Arrays#asList
                        map.put(ecs, new ArrayList<>(Arrays.asList(PLACEHOLDER)));
                    }
                }
            }
        }
    }

    /**
     * Once a subscriber has been unsubscribed any method annotated with {@link Listener} no longer
     * will be called on {@link BusImpl#post}
     *
     * @param subscriber Any {@link Object} that has been subscribed using {@link BusImpl#subscribe}.
     */
    @Override
    public void unsubscribe(Object subscriber) {
        // using removeIf is noticeably faster than any alternative (open for discussion)
        for (List<Site> cls : this.map.values()) cls.removeIf(c -> c.s == subscriber);
    }

    /**
     * @param event Any {@link T}, when {@code event} is posted it invokes all
     *              methods annotated with {@link Listener} and with either 0 or 1 parameter(s),
     *              if 1 parameter is present it must be of matching type as the class specified
     *              in {@link Listener#value}. Only if an instance of the method's containing
     *              class has been subscribed using {@link BusImpl#subscribe} will it be invoked.
     */
    @Override
    public void post(T event) {
        final List<Site> cls = this.map.get(event.getClass());
        if (cls != null) for (int i = 0, clsSize = cls.size(); i < clsSize; i++) {
            try {
                final Site cl = cls.get(i);
                final Method m = cl.m;
                final Object sub = cl.s;
                if (cl.nP) m.invoke(sub);
                else m.invoke(sub, event);
            } catch (IllegalAccessException | InvocationTargetException | IndexOutOfBoundsException ignored) {
            }
        }
    }
}
