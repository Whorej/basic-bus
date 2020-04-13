package me.zane.basicbus.api.bus.impl;

import me.zane.basicbus.api.annotation.Listener;
import me.zane.basicbus.api.bus.Bus;
import me.zane.basicbus.api.invocation.Invoker;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public final class BaseEventBus implements Bus {

    private final Map<Class<?>, List<CallLocation>> eventClassMethodMap = new HashMap<>();

    private final Invoker invoker;

    public BaseEventBus(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void subscribe(Object subscriber) {
        final Method[] methods = subscriber.getClass().getDeclaredMethods();
        final Map<Class<?>, List<CallLocation>> eventClassMethodMapRef = eventClassMethodMap;
        for (int i = methods.length - 1; i >= 0; i--) {
            final Method method = methods[i];
            final Listener listener = method.getAnnotation(Listener.class);
            if (listener != null) {
                final Class<?>[] params = method.getParameterTypes();
                final int paramsLength = params.length;
                if (paramsLength <= 1) {
                    final Class<?> eventClass = listener.value();

                    if (paramsLength == 1 && eventClass != params[0]) {
                        continue;
                    }

                    final CallLocation callLoc = new CallLocation(subscriber, method);

                    if (eventClassMethodMapRef.containsKey(eventClass)) {
                        eventClassMethodMapRef.get(eventClass).add(callLoc);
                    } else {
                        eventClassMethodMapRef.put(eventClass, new CopyOnWriteArrayList<>(Collections.singletonList(callLoc)));
                    }
                }
            }
        }
    }

    @Override
    public void unsubscribe(Object subscriber) {
        final Map<Class<?>, List<CallLocation>> eventClassMethodMapRef = eventClassMethodMap;
        for (final List<CallLocation> callLocations : eventClassMethodMapRef.values()) {
            for (int i = 0, callLocationsSize = callLocations.size(); i < callLocationsSize; i++) {
                final CallLocation callLocation = callLocations.get(i);
                if (callLocation.subscriber == subscriber) {
                    callLocations.remove(callLocation);
                }
            }
        }
    }

    @Override
    public final void publish(Object event) {
        final List<CallLocation> callLocations = eventClassMethodMap.get(event.getClass());
        if (callLocations != null) {
            for (int callLocationsSize = callLocations.size(), i = 0; i < callLocationsSize; i++) {
                final CallLocation callLocation = callLocations.get(i);
                final Method method = callLocation.call;
                final Object sub = callLocation.subscriber;

                if (callLocation.noParams) {
                    invoker.invoke(sub, method);
                } else {
                    invoker.invoke(sub, method, event);
                }
            }
        }
    }


    private static class CallLocation {

        private final Object subscriber;
        private final Method call;
        private final boolean noParams;

        public CallLocation(Object subscriber, Method call) {
            this.subscriber = subscriber;
            call.setAccessible(true);
            this.call = call;
            noParams = call.getParameterCount() == 0;
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
