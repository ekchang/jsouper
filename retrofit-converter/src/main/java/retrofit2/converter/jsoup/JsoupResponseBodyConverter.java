package retrofit2.converter.jsoup;

import com.ekchang.jsouper.ElementAdapter;
import java.io.IOException;
import okhttp3.ResponseBody;
import org.jsoup.nodes.Document;
import retrofit2.Converter;

public class JsoupResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final ElementAdapter<T> adapter;

  public JsoupResponseBodyConverter(ElementAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override
  public T convert(ResponseBody value) throws IOException {
    Document document = JsoupDocumentConverter.INSTANCE.convert(value);
    return adapter.fromElement(document);
  }
}
