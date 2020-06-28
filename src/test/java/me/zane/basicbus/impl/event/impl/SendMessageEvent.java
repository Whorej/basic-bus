package me.zane.basicbus.impl.event.impl;

import me.zane.basicbus.impl.event.Event;

public final class SendMessageEvent implements Event {

    private final String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
