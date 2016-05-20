package com.ekchang.jsouper.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Annotates another annotation, causing it to specialize how values are encoded and decoded. */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface SoupQualifier {
}