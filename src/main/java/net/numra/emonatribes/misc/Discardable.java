package net.numra.emonatribes.misc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Indicates that a methods return is safely discardable
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Discardable {
}
