package me.zane.basicbus.api.bus;

import java.lang.reflect.Method;

/**
 * @since 1.4.0
 */

final class Site {

    final Object s;
    final Method m;
    final boolean nP;

    Site(Object s, Method m) {
        this.s = s;
        if (!m.isAccessible()) {
            m.setAccessible(true);
        }
        this.m = m;
        nP = m.getParameterCount() == 0;
    }
}
