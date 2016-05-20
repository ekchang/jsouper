package com.ekchang.jsouper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Jsouper {

  private static final List<ElementAdapter.Factory> BUILT_IN_FACTORIES = new ArrayList<>(3);

  static {
    BUILT_IN_FACTORIES.add(StandardElementAdapters.FACTORY);
    BUILT_IN_FACTORIES.add(CollectionElementAdapter.FACTORY);
    BUILT_IN_FACTORIES.add(ClassElementAdapter.FACTORY);
  }

  private List<ElementAdapter.Factory> factories = new ArrayList<>();

  public Jsouper(Builder builder) {
    List<ElementAdapter.Factory> factories =
        new ArrayList<>(builder.factories.size() + BUILT_IN_FACTORIES.size());
    factories.addAll(builder.factories);
    factories.addAll(BUILT_IN_FACTORIES);
    this.factories = Collections.unmodifiableList(factories);
  }

  public <T> ElementAdapter<T> adapter(Type type) {
    return adapter(type, Util.NO_ANNOTATIONS);
  }

  // TODO Add in the Moshi caching logic eventually
  public <T> ElementAdapter<T> adapter(Type type, Set<? extends Annotation> annotations) {
    for (int i = 0, size = factories.size(); i < size; i++) {
      ElementAdapter<T> result =
          (ElementAdapter<T>) factories.get(i).create(type, annotations, this);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

  public static class Builder {
    final List<ElementAdapter.Factory> factories = new ArrayList<>();

    public <T> Builder add(final Type type, final ElementAdapter<T> elementAdapter) {
      if (type == null) throw new IllegalArgumentException("type == null");
      if (elementAdapter == null) throw new IllegalArgumentException("elementAdapter == null");

      return add(new ElementAdapter.Factory() {
        @Override
        public ElementAdapter<?> create(Type targetType, Set<? extends Annotation> annotations,
            Jsouper jsouper) {
          return Util.typesMatch(type, targetType) ? elementAdapter : null;
        }
      });
    }

    public Builder add(ElementAdapter.Factory factory) {
      factories.add(factory);
      return this;
    }

    public Jsouper build() {
      return new Jsouper(this);
    }
  }
}
