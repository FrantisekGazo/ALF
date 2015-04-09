package com.f3rog.alf.dagger.qualifier;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Class {@link ForApplication} is Dagger qualifier annotation
 *
 * @author f3rog
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}