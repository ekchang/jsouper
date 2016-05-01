package retrofit2.converter.jsoup;

import java.io.IOException;
import okhttp3.ResponseBody;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import retrofit2.Converter;

public class JsoupDocumentConverter implements Converter<ResponseBody, Document> {
  public static final JsoupDocumentConverter INSTANCE = new JsoupDocumentConverter();

  private JsoupDocumentConverter() {
  }

  @Override
  public Document convert(ResponseBody value) throws IOException {
    return Jsoup.parse(value.string());
  }
}
