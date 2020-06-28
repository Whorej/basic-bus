package me.zane.basicbus.api.filter;

public interface Filter<T> {

    boolean validate(T object);

}
