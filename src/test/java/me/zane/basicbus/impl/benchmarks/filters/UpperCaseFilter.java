package me.zane.basicbus.impl.benchmarks.filters;

import me.zane.basicbus.api.filter.Filter;

public final class UpperCaseFilter implements Filter<String> {
    @Override
    public boolean validate(String object) {
        return object.equals(object.toUpperCase());
    }
}
