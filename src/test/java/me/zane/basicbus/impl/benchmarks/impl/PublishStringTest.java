package me.zane.basicbus.impl.benchmarks.impl;

import me.zane.basicbus.api.bus.Bus;
import me.zane.basicbus.api.bus.BusImpl;
import me.zane.basicbus.impl.benchmarks.Subscriber;
import me.zane.basicbus.impl.benchmarks.Test;
import me.zane.basicbus.impl.event.Event;
import me.zane.basicbus.impl.event.impl.SendMessageEvent;

public final class PublishStringTest implements Test {

    @Override
    public void test() {
        final Bus<Event> basicBus = new BusImpl<>();

        basicBus.subscribe(new Subscriber());

        final long currentTime = System.currentTimeMillis();

        for (long i = iterations - 1; i >= 0; i--) {
            basicBus.post(new SendMessageEvent(""));
        }

        final long finalTime = System.currentTimeMillis();

        System.out.println("Published " + iterations + " Event(s) in " +  (finalTime - currentTime) + "ms");
    }
}
