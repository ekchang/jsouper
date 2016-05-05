package com.ekchang.jsouper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

final class Util {
  public static final Set<Annotation> NO_ANNOTATIONS = Collections.emptySet();

  public static boolean typesMatch(Type pattern, Type candidate) {
    // TODO: permit raw types (like Set.class) to match non-raw candidates (like Set<Long>).
    return pattern.equals(candidate);
  }
}
