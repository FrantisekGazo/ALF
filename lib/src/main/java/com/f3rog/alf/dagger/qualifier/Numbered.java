package com.f3rog.alf.dagger.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class {@link Numbered} is Dagger qualifier annotation
 *
 * @author f3rog
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface Numbered {
    int value() default 0;
}
