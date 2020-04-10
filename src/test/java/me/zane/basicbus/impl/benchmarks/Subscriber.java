package me.zane.basicbus.impl.benchmarks;

import me.zane.basicbus.api.annotation.Listener;

public class Subscriber {

    @Listener(Event.class)
    public final void onEvent() {}

}
