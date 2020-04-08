package me.zane.basicbus.api.event;

public class Cancellable {

    private boolean cancelled;

    public void setCancelled() {
        cancelled = true;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
