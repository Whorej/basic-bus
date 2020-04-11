package me.zane.basicbus.impl;

import me.zane.basicbus.impl.benchmarks.impl.PublishStringTest;
import me.zane.basicbus.impl.benchmarks.impl.SubscribeUnsubscribeTest;

public class Main {

    public static void main(String... arguments) {
        final SubscribeUnsubscribeTest subscribeUnsubscribeTest = new SubscribeUnsubscribeTest();
        final PublishStringTest publishStringTest = new PublishStringTest();

        subscribeUnsubscribeTest.test();
        publishStringTest.test();
    }
}
