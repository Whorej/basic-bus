package me.zane.basicbus.impl.benchmarks.filters;

import me.zane.basicbus.api.filter.Filter;

public final class AlphanumericFilter implements Filter<String> {
    @Override
    public boolean validate(String object) {
        return object.matches("[A-Za-z0-9]+");
    }
}
