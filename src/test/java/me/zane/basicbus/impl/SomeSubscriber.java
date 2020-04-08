package me.zane.basicbus.impl;

import me.zane.basicbus.api.annotation.Listener;
import me.zane.basicbus.api.bus.IListener;

/**
 * To subscribe something it must implement the {@link IListener} interface and implement
 * the {@link IListener#isActive()} method.
 */

public class SomeSubscriber implements IListener {

    public boolean someCondition = true;

    /**
     * @see Listener
     * <h2>
     *  Example 1: Method with no parameters, annotated with the {@link Listener} annotation
     * </h2>
     * <p>
     *  This method will only get invoked while <code>someCondition</code> is true and an
     *  instance of SomeSubscriber has been registered as a subscriber via {@link me.zane.basicbus.api.bus.Bus#subscribe(IListener)}.
     * </p>
     */
    @Listener(SomeCancellableEvent.class)
    public final void onSomethingEvent() {
        System.out.println("SomeCancellableEvent was invoked");
    }

    /**
     * <h2>
     *  Example 2: Method with a single parameter
     * </h2>
     * <p>
     *  The method can have a single parameter unlike Example 1, this parameter has to be
     *  the same type as the <code>Class<?></code> specified as {@link Listener#value()}.
     * </p>
     */
    @Listener(SomeOtherEvent.class)
    public final void onSomeOtherEvent(SomeOtherEvent event) {
        System.out.println(event.getSomeString());
    }

    @Override
    public boolean isActive() {
        // IListener will only be "listening" when someCondition is true.
        return someCondition;
    }

}
