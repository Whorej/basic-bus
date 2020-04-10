package me.zane.basicbus.api.bus.impl;

import me.zane.basicbus.api.annotation.Listener;
import me.zane.basicbus.api.bus.Bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class BaseEventBus implements Bus {

    private final ConcurrentHashMap<Object, ConcurrentHashMap<Class<?>, List<CallLocation>>> subscriberMethodRegistry = new ConcurrentHashMap<>();

    @Override
    public void subscribe(Object listener) {
        final ConcurrentHashMap<Class<?>, List<CallLocation>> classCallLocationMap = new ConcurrentHashMap<>();
        for (int i = listener.getClass().getDeclaredMethods().length - 1; i >= 0; i--) {
            final Method method = listener.getClass().getDeclaredMethods()[i];
            if (method.isAnnotationPresent(Listener.class) && (method.getParameterCount() == 0 || method.getParameterCount() == 1)) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }

                final Class<?> eventClass = method.getAnnotation(Listener.class).value();

                if (method.getParameterCount() == 1) {
                    if (eventClass != method.getParameterTypes()[0]) {
                        continue;
                    }
                }

                if (subscriberMethodRegistry.containsKey(eventClass)) {
                    classCallLocationMap.get(eventClass).add(new CallLocation(listener, method));
                } else {
                    classCallLocationMap.put(eventClass, Collections.singletonList(new CallLocation(listener, method)));
                }
            }
        }

        if (subscriberMethodRegistry.containsKey(listener)) {
            subscriberMethodRegistry.get(listener).putAll(classCallLocationMap);
        } else {
            subscriberMethodRegistry.put(listener, classCallLocationMap);
        }
    }

    @Override
    public void unsubscribe(Object subscriber) {
        subscriberMethodRegistry.remove(subscriber);
    }

    @Override
    public final void publish(Object event) {
        for (final ConcurrentHashMap<Class<?>, List<CallLocation>> classCallLocationMap : subscriberMethodRegistry.values()) {
            for (Class<?> eventClass : classCallLocationMap.keySet()) {
                final Class<?> clazz = event.getClass();
                if (clazz == eventClass) {
                    for (final CallLocation callLocation : classCallLocationMap.get(eventClass)) {
                        final Method method = callLocation.call;
                        try {
                            if (method.getParameterCount() == 1) {
                                method.invoke(callLocation.subscriber, event);
                            } else {
                                method.invoke(callLocation.subscriber);
                            }
                        } catch (IllegalAccessException | InvocationTargetException ignored) {

                        }
                    }
                }
            }
        }
    }


    private class CallLocation {

        private final Object subscriber;
        private final Method call;

        public CallLocation(Object subscriber, Method call) {
            this.subscriber = subscriber;
            this.call = call;
        }

        @Override
        public String toString() {
            return "CallLocation {\n" +
                    "   instance: " + subscriber.getClass().getCanonicalName() + "\n" +
                    "   call: " + call.getName() + "\n" +
                    '}';
        }
    }
}
