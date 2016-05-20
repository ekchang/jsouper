package com.ekchang.jsouper.annotations;

import com.ekchang.jsouper.ElementAdapter;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotates a class, specifying the query to use in the generated {@link ElementAdapter#query()}.
 * This lets you specify the query without creating an explicit {@link ElementAdapter}
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface SoupQuery {
  String value();
}
