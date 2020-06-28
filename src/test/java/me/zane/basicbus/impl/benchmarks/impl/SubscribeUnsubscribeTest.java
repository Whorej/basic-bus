package me.zane.basicbus.impl.benchmarks.impl;

import me.zane.basicbus.api.bus.Bus;
import me.zane.basicbus.api.bus.BusImpl;
import me.zane.basicbus.impl.benchmarks.Subscriber;
import me.zane.basicbus.impl.benchmarks.Test;
import me.zane.basicbus.impl.event.Event;
import me.zane.basicbus.impl.event.impl.SubscribedEvent;

public final class SubscribeUnsubscribeTest implements Test {

    @Override
    public void test() {
        final Bus<Event> basicBus = new BusImpl<>();
        final long currentTime = System.currentTimeMillis();

        for (long i = iterations - 1; i >= 0; i--) {
            final Subscriber sub = new Subscriber();
            basicBus.subscribe(sub);
            basicBus.post(new SubscribedEvent());
            basicBus.unsubscribe(sub);
        }

        final long finalTime = System.currentTimeMillis();

        System.out.println("Subscribed and unsubscribed " + iterations + " Subscriber(s) in " + (finalTime - currentTime) + "ms");
    }
}
