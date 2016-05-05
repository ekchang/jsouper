package com.ekchang.jsouper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import org.jsoup.nodes.Element;

public abstract class ElementAdapter<T> {
  public abstract T fromElement(Element element) throws IOException;

  public abstract String query();

  public interface Factory {
    ElementAdapter<?> create(Type type, Set<? extends Annotation> annotations, Jsouper jsouper);
  }
}
