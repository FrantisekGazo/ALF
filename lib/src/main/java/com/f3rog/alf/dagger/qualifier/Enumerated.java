package com.f3rog.alf.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class {@link Enumerated} is Dagger qualifier annotation
 *
 * @author f3rog
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface Enumerated {
    DefaultEnumType value() default DefaultEnumType.NONE;
}
