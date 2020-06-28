package me.zane.basicbus.api.annotations;

import me.zane.basicbus.api.filter.Filter;

import java.lang.annotation.*;

/**
 * @since 1.4.2
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Filters {

    Class<? extends Filter<?>>[] value();

}
