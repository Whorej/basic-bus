package me.zane.basicbus.api.bus;

public interface Bus {

    void subscribe(Object listener);

    void unsubscribe(Object listener);

    void publish(Object event);
}
