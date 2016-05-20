package com.ekchang.jsouper;

import com.ekchang.jsouper.annotations.Experimental;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import org.jsoup.nodes.Element;

public abstract class ElementAdapter<T> {
  public abstract T fromElement(Element element) throws IOException;

  /**
   * I have some concerns about officially supporting this API. Essentially this is needed for the
   * standard primitive deserializers -- we need to run the query at the time of deserialization
   * because {@link #fromElement(Element)} will perform the correct casting/conversion.
   * <br><br>
   * This essentially lets you reuse a particular {@link ElementAdapter} to deserialize different
   * objects without needing to specify the {@link #query()} upfront.
   * <br><br>
   * This may replace {@link #query()} or be absorbed by it in the future.
   *
   * @throws IOException
   */
  @Experimental
  T fromElement(Element element, String query) throws IOException {
    return fromElement(element.select(query).first());
  }

  public abstract String query();

  public interface Factory {
    ElementAdapter<?> create(Type type, Set<? extends Annotation> annotations, Jsouper jsouper);
  }
}
