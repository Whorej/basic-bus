package me.zane.basicbus.impl.benchmarks;

import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.impl.event.impl.SendMessageEvent;
import me.zane.basicbus.impl.event.impl.SubscribedEvent;

public final class Subscriber {

    @Listener({SubscribedEvent.class, SendMessageEvent.class})
    public final void onMessage(final SendMessageEvent event) {
        System.out.println();
    }

}
