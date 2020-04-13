package me.zane.basicbus.api.invocation;

import java.lang.reflect.Method;

@FunctionalInterface
public interface Invoker {

    void invoke(Object instance, Method method, Object... parameters);

}
