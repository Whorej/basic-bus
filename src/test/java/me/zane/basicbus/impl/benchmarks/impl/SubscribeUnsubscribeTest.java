package me.zane.basicbus.impl.benchmarks.impl;

import me.zane.basicbus.api.bus.impl.BaseEventBus;
import me.zane.basicbus.impl.benchmarks.Subscriber;
import me.zane.basicbus.impl.benchmarks.Test;

public class SubscribeUnsubscribeTest implements Test {

    @Override
    public void test() {
        final BaseEventBus basicBus = new BaseEventBus();
        long currentTime = System.nanoTime();

        for (long i = 0; i < iterations; i++) {
            final Subscriber sub = new Subscriber();
            basicBus.subscribe(sub);
            basicBus.unsubscribe(sub);
        }

        System.out.println("Subscribed and unsubscribed " + iterations + " Subscriber(s) in " + (System.nanoTime() - currentTime) * 1.0E-6D + "ms");
    }
}
