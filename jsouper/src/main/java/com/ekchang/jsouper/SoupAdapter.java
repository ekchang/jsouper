package com.ekchang.jsouper;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Annotates a field or class, specifying the discrete Adapter class to use */
@Retention(RUNTIME)
@Target({ FIELD, TYPE })
@SoupQualifier
public @interface SoupAdapter {
  Class value();
}
