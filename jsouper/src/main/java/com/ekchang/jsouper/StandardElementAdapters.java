package com.ekchang.jsouper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import org.jsoup.nodes.Element;

final class StandardElementAdapters {
  public static final ElementAdapter.Factory FACTORY = new ElementAdapter.Factory() {

    @Override
    public ElementAdapter<?> create(Type type, Set<? extends Annotation> annotations,
        Jsouper jsouper) {
      if (!annotations.isEmpty()) return null;
      if (type == String.class) return STRING_ELEMENT_ADAPTER;
      return null;
    }
  };

  static final ElementAdapter<String> STRING_ELEMENT_ADAPTER = new ElementAdapter<String>() {
    @Override
    public String fromElement(Element element) throws IOException {
      return element.text();
    }

    @Override
    public String query() {
      return null;
    }
  };
}
