package me.zane.basicbus.impl;

public class SomeOtherEvent {

    private final String someString;

    public SomeOtherEvent(String someString) {
        this.someString = someString;
    }

    public String getSomeString() {
        return someString;
    }

}
