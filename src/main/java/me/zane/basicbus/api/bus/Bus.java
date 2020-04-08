package me.zane.basicbus.api.bus;

public interface Bus {

    void subscribe(IListener listener);

    void publish(Object object);
}
