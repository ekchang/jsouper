package com.ekchang.jsouper;

import com.ekchang.jsouper.annotations.SoupQualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

final class Util {
  public static final Set<Annotation> NO_ANNOTATIONS = Collections.emptySet();

  public static boolean typesMatch(Type pattern, Type candidate) {
    // TODO: permit raw types (like Set.class) to match non-raw candidates (like Set<Long>).
    return pattern.equals(candidate);
  }

  public static Set<? extends Annotation> soupAnnotations(AnnotatedElement annotatedElement) {
    return soupAnnotations(annotatedElement.getAnnotations());
  }

  public static Set<? extends Annotation> soupAnnotations(Annotation[] annotations) {
    Set<Annotation> result = null;
    for (Annotation annotation : annotations) {
      if (annotation.annotationType().isAnnotationPresent(SoupQualifier.class)) {
        if (result == null) result = new LinkedHashSet<>();
        result.add(annotation);
      }
    }
    return result != null ? Collections.unmodifiableSet(result) : Util.NO_ANNOTATIONS;
  }
}
