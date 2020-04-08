package me.zane.basicbus.api.bus;

import me.zane.basicbus.api.annotation.Listener;

/**
 * @see Listener
 * I prefixed this class with "I" to avoid confusion with the annotation {@link Listener}.
 */
public interface IListener {

    boolean isActive();

}
