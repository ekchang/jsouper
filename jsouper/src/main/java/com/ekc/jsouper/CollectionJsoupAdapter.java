package com.ekc.jsouper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/** Converts collection types to Elements containing their converted contents. */
abstract class CollectionJsoupAdapter<C extends Collection<T>, T> extends ElementAdapter<C> {
  public static final ElementAdapter.Factory FACTORY = new ElementAdapter.Factory() {
    @Override
    public ElementAdapter<?> create(Type type, Set<? extends Annotation> annotations,
        Jsouper jsouper) {
      Class<?> rawType = Types.getRawType(type);
      if (!annotations.isEmpty()) return null;
      if (rawType == List.class || rawType == Collection.class) {
        return newArrayListAdapter(type, jsouper);
      } else if (rawType == Set.class) {
        return newLinkedHashSetAdapter(type, jsouper);
      }
      return null;
    }
  };

  private final ElementAdapter<T> elementAdapter;

  private CollectionJsoupAdapter(ElementAdapter<T> elementAdapter) {
    this.elementAdapter = elementAdapter;
  }

  static <T> ElementAdapter<Collection<T>> newArrayListAdapter(Type type, Jsouper jsouper) {
    Type elementType = Types.collectionElementType(type, Collection.class);
    ElementAdapter<T> elementAdapter = jsouper.adapter(elementType);
    return new CollectionJsoupAdapter<Collection<T>, T>(elementAdapter) {
      @Override
      public String query() {
        return null;
      }

      @Override
      Collection<T> newCollection() {
        return new ArrayList<>();
      }
    };
  }

  static <T> ElementAdapter<Set<T>> newLinkedHashSetAdapter(Type type, Jsouper jsouper) {
    Type elementType = Types.collectionElementType(type, Collection.class);
    ElementAdapter<T> elementAdapter = jsouper.adapter(elementType);
    return new CollectionJsoupAdapter<Set<T>, T>(elementAdapter) {
      @Override
      public String query() {
        return null;
      }

      @Override
      Set<T> newCollection() {
        return new LinkedHashSet<>();
      }
    };
  }

  abstract C newCollection();

  @Override
  public C fromElement(Element element) throws IOException {
    C result = newCollection();

    if (elementAdapter.query() == null) {
      throw new NullPointerException("query must not be null for a collection");
    }

    Elements elements = element.select(elementAdapter.query());
    for (Element aElement : elements) {
      result.add(elementAdapter.fromElement(aElement));
    }
    return result;
  }

  // Evaluate if we really need a method to write to HTML
  //@Override public void toElement(JsonWriter writer, C value) throws IOException {
  //  writer.beginArray();
  //  for (T element : value) {
  //    elementAdapter.toJson(writer, element);
  //  }
  //  writer.endArray();
  //}

  @Override
  public String toString() {
    return elementAdapter + ".collection()";
  }
}