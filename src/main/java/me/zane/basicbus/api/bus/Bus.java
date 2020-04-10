package me.zane.basicbus.api.bus;

public interface Bus {

    void subscribe(Object subscriber);

    void unsubscribe(Object subscriber);

    void publish(Object event);
}
