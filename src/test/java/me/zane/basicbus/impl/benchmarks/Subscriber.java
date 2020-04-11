package me.zane.basicbus.impl.benchmarks;

import me.zane.basicbus.api.annotation.Listener;
import me.zane.basicbus.impl.benchmarks.event.Event;

public class Subscriber {

    @Listener(String.class)
    public final void onEvent() {
    }

}
