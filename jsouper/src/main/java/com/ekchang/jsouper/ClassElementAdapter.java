/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ekchang.jsouper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jsoup.nodes.Element;

/**
 * Emits a regular class as a JSON object by mapping Java fields to JSON object properties. Fields
 * of classes in {@code java.*}, {@code javax.*} and {@code android.*} are omitted from both
 * serialization and deserialization unless they are either public or protected.
 */
final class ClassElementAdapter<T> extends ElementAdapter<T> {
  public static final Factory FACTORY = new Factory() {
    @Override
    public ElementAdapter<?> create(Type type, Set<? extends Annotation> annotations,
        Jsouper jsouper) {
      Class<?> rawType = Types.getRawType(type);
      if (rawType.isInterface() || rawType.isEnum()) return null;
      if (isPlatformType(rawType)) {
        throw new IllegalArgumentException("Platform " + type + " annotated " + annotations
            + " requires explicit ElementAdapter to be registered");
      }

      if (rawType.getEnclosingClass() != null && !Modifier.isStatic(rawType.getModifiers())) {
        if (rawType.getSimpleName().isEmpty()) {
          throw new IllegalArgumentException(
              "Cannot serialize anonymous class " + rawType.getName());
        } else {
          throw new IllegalArgumentException(
              "Cannot serialize non-static nested class " + rawType.getName());
        }
      }
      if (Modifier.isAbstract(rawType.getModifiers())) {
        throw new IllegalArgumentException("Cannot serialize abstract class " + rawType.getName());
      }

      // If a SoupAdapter was declared, try returning an instance of that class
      if (!annotations.isEmpty()) {
        for (Annotation annotation : annotations) {
          if (annotation instanceof SoupAdapter) {
            try {
              return (ElementAdapter<?>) ClassFactory.get(((SoupAdapter) annotation).value())
                  .newInstance();
            } catch (InvocationTargetException e) {
              Throwable targetException = e.getTargetException();
              if (targetException instanceof RuntimeException) {
                throw (RuntimeException) targetException;
              }
              if (targetException instanceof Error) throw (Error) targetException;
            } catch (IllegalAccessException e) {
              throw new AssertionError();
            } catch (InstantiationException e) {
              throw new RuntimeException(e);
            }
          }
        }
      }

      final SoupQuery elementQueryAnnotation = rawType.getAnnotation(SoupQuery.class);

      ClassFactory<Object> classFactory = ClassFactory.get(rawType);
      Map<String, FieldBinding<?>> fields = new TreeMap<>();
      for (Type t = type; t != Object.class; t = Types.getGenericSuperclass(t)) {
        createFieldBindings(jsouper, t, fields);
      }
      //return new ClassElementAdapter<>(classFactory, fields).nullSafe();
      return new ClassElementAdapter<>(classFactory, fields, elementQueryAnnotation.value());
    }

    /** Creates a field binding for each of declared field of {@code type}. */
    private void createFieldBindings(Jsouper jsouper, Type type,
        Map<String, FieldBinding<?>> fieldBindings) {
      Class<?> rawType = Types.getRawType(type);
      boolean platformType = isPlatformType(rawType);
      for (Field field : rawType.getDeclaredFields()) {
        if (!includeField(platformType, field.getModifiers())) continue;

        // Look up a type adapter for this type.
        Type fieldType = Types.resolve(type, rawType, field.getGenericType());
        Set<? extends Annotation> annotations = Util.soupAnnotations(field);
        ElementAdapter<Object> adapter = jsouper.adapter(fieldType, annotations);

        // Create the binding between field and Element.
        field.setAccessible(true);
        FieldBinding<Object> fieldBinding = new FieldBinding<>(field, adapter);

        // Store it using the field's name. If there was already a field with this name, fail!
        //Json jsonAnnotation = field.getAnnotation(Json.class);
        //String name = jsonAnnotation != null ? jsonAnnotation.name() : field.getName();
        String name = field.getName();
        FieldBinding<?> replaced = fieldBindings.put(name, fieldBinding);
        if (replaced != null) {
          throw new IllegalArgumentException(
              "Conflicting fields:\n" + "    " + replaced.field + "\n" + "    "
                  + fieldBinding.field);
        }
      }
    }

    /**
     * Returns true if {@code rawType} is built in. We don't reflect on private fields of platform
     * types because they're unspecified and likely to be different on Java vs. Android.
     */
    private boolean isPlatformType(Class<?> rawType) {
      return rawType.getName().startsWith("java.") || rawType.getName().startsWith("javax.")
          || rawType.getName().startsWith("android.");
    }

    /** Returns true if fields with {@code modifiers} are included in the emitted JSON. */
    private boolean includeField(boolean platformType, int modifiers) {
      if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) return false;
      return Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || !platformType;
    }
  };

  private final ClassFactory<T> classFactory;
  private final Map<String, FieldBinding<?>> elementFields;
  private final String query;

  ClassElementAdapter(ClassFactory<T> classFactory, Map<String, FieldBinding<?>> elementFields,
      String query) {
    this.classFactory = classFactory;
    this.elementFields = elementFields;
    this.query = query;
  }

  @Override
  public String query() {
    return query;
  }

  @Override
  public T fromElement(Element element) throws IOException {
    T result;
    try {
      result = classFactory.newInstance();
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      Throwable targetException = e.getTargetException();
      if (targetException instanceof RuntimeException) throw (RuntimeException) targetException;
      if (targetException instanceof Error) throw (Error) targetException;
      throw new RuntimeException(targetException);
    } catch (IllegalAccessException e) {
      throw new AssertionError();
    }

    // Find the first element matching the query
    element = element.select(query()).first();

    final Iterator<Map.Entry<String, FieldBinding<?>>> iterator =
        elementFields.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, FieldBinding<?>> pair = iterator.next();
      try {
        pair.getValue().read(element, result);
      } catch (IllegalAccessException e) {
        throw new AssertionError();
      }
    }

    return result;
    //try {
    //  element.beginObject();
    //  while (element.hasNext()) {
    //    String name = element.nextName();
    //    FieldBinding<?> fieldBinding = elementFields.get(name);
    //    if (fieldBinding != null) {
    //      fieldBinding.read(element, result);
    //    } else {
    //      element.skipValue();
    //    }
    //  }
    //  element.endObject();
    //  return result;
    //} catch (IllegalAccessException e) {
    //  throw new AssertionError();
    //}
  }

  //@Override public void toJson(JsonWriter writer, T value) throws IOException {
  //  try {
  //    writer.beginObject();
  //    for (Map.Entry<String, FieldBinding<?>> entry : elementFields.entrySet()) {
  //      writer.name(entry.getKey());
  //      entry.getValue().write(writer, value);
  //    }
  //    writer.endObject();
  //  } catch (IllegalAccessException e) {
  //    throw new AssertionError();
  //  }
  //}

  @Override
  public String toString() {
    return "ElementAdapter(" + classFactory + ")";
  }

  static class FieldBinding<T> {
    private final Field field;
    private final ElementAdapter<T> adapter;

    public FieldBinding(Field field, ElementAdapter<T> adapter) {
      this.field = field;
      this.adapter = adapter;
    }

    void read(Element element, Object value) throws IOException, IllegalAccessException {
      T fieldValue = adapter.fromElement(element);
      field.set(value, fieldValue);
    }

    //@SuppressWarnings("unchecked") // We require that field's values are of type T.
    //void write(JsonWriter writer, Object value) throws IllegalAccessException, IOException {
    //  T fieldValue = (T) field.get(value);
    //  adapter.toJson(writer, fieldValue);
    //}
  }
}
