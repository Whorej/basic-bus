package me.zane.basicbus.impl.benchmarks.impl;

import me.zane.basicbus.api.bus.impl.BaseEventBus;
import me.zane.basicbus.api.invocation.impl.ReflectionInvoker;
import me.zane.basicbus.impl.benchmarks.Subscriber;
import me.zane.basicbus.impl.benchmarks.Test;

public final class SubscribeUnsubscribeTest implements Test {

    @Override
    public void test() {
        final BaseEventBus basicBus = new BaseEventBus(new ReflectionInvoker());
        final long currentTime = System.currentTimeMillis();

        for (long i = iterations - 1; i >= 0; i--) {
            final Subscriber sub = new Subscriber();
            basicBus.subscribe(sub);
            basicBus.unsubscribe(sub);
        }

        final long finalTime = System.currentTimeMillis();

        System.out.println("Subscribed and unsubscribed " + iterations + " Subscriber(s) in " + (finalTime - currentTime) + "ms");
    }
}
