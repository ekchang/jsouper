package retrofit2.converter.jsoup;

import com.ekchang.jsouper.ElementAdapter;
import com.ekchang.jsouper.Jsouper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class JsoupConverterFactory extends Converter.Factory {
  private final Jsouper jsouper;

  private JsoupConverterFactory(Jsouper jsouper) {
    if (jsouper == null) throw new NullPointerException("jsouper == null");
    this.jsouper = jsouper;
  }

  @Override
  public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
      Retrofit retrofit) {
    ElementAdapter<?> adapter;

    // Fail and let other converters take over
    try {
      adapter = jsouper.adapter(type);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      return null;
    }

    return new JsoupResponseBodyConverter<>(adapter);
  }

  public static Converter.Factory create() {
    return new JsoupConverterFactory(new Jsouper.Builder().build());
  }

  public static Converter.Factory create(Jsouper jsouper) {
    return new JsoupConverterFactory(jsouper);
  }
}
