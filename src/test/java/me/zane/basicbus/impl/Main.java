package me.zane.basicbus.impl;

import me.zane.basicbus.api.bus.impl.BaseEventBus;


public class Main {

    /*
     * BaseEventBus is not explicit typed and publish takes an Object as a parameter
     * meaning it can double as a message bus.
     */
    public static final BaseEventBus BASIC_BUS;

    static {
        BASIC_BUS = new BaseEventBus();
    }

    public static void main(String... arguments) {
        BASIC_BUS.subscribe(new SomeSubscriber());

        BASIC_BUS.publish(new SomeOtherEvent("Hello, world!"));

        BASIC_BUS.publish("Some String");
    }
}
