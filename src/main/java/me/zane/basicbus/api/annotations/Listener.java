package me.zane.basicbus.api.annotations;

import java.lang.annotation.*;



/**
 * When a method in a subscribed class is annotated
 * @since 1.4.0
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listener {

    Class<?> value();
}
