package me.zane.basicbus.impl.benchmarks;

import me.zane.basicbus.api.annotations.Listener;

public final class Subscriber {

    @Listener(Event.class)
    public void onEvent() {}
}
