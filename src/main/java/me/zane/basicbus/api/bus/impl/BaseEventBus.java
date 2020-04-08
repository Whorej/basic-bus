package me.zane.basicbus.api.bus.impl;

import me.zane.basicbus.api.annotation.Listener;
import me.zane.basicbus.api.bus.Bus;
import me.zane.basicbus.api.bus.IListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public final class BaseEventBus implements Bus {

    public final Map<Class<?>, List<CallLocation>> eventRegistry = new HashMap<>();

    @Override
    public final void publish(Object object) {
        for (Class<?> eventClass : eventRegistry.keySet()) {
            final Class<?> clazz = object.getClass();
            if (clazz == eventClass) {
                for (CallLocation callLocation : eventRegistry.get(clazz)) {
                    if (callLocation.listener.isActive()) {
                        final Method method = callLocation.call;

                        try {
                            if (method.getParameterCount() == 1) {
                                method.invoke(callLocation.listener, object);
                            } else {
                                method.invoke(callLocation.listener);
                            }
                        } catch (IllegalAccessException | InvocationTargetException ignored) {

                        }
                    }
                }
            }
        }
    }

    /*
     * @param
     */
    @Override
    public void subscribe(IListener listener) {
        for (final Method method : listener.getClass().getDeclaredMethods()) {
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

                if (eventRegistry.containsKey(eventClass)) {
                    eventRegistry.get(eventClass).add(new CallLocation(listener, method));
                } else {
                    eventRegistry.put(eventClass, Collections.singletonList(new CallLocation(listener, method)));
                }
            }
        }
    }

    private class CallLocation {

        private final IListener listener;
        private final Method call;

        public CallLocation(IListener listener, Method call) {
            this.listener = listener;
            this.call = call;
        }

        /*
         * Used for debugging/testing
         */

        @Override
        public String toString() {
            return "CallLocation {\n" +
                    "   instance: " + listener.getClass().getCanonicalName() + "\n" +
                    "   call: " + call.getName() + "\n" +
                    '}';
        }
    }
}
