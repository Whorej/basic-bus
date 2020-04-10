package me.zane.basicbus.impl.benchmarks.impl;

import me.zane.basicbus.api.bus.impl.BaseEventBus;
import me.zane.basicbus.impl.benchmarks.Test;

public class PublishStringTest implements Test {

    @Override
    public void test() {
        final BaseEventBus basicBus = new BaseEventBus();

        final long currentTime = System.currentTimeMillis();

        for (long i = iterations - 1; i >= 0; i--) {
            basicBus.publish(" ");
        }

        final long finalTime = System.currentTimeMillis();

        System.out.println("Published " + iterations + " Event(s) in " +  (finalTime - currentTime) + "ms");
    }
}
