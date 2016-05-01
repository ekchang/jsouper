package com.ekc.jsouper.sample;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PlayStore {
  public static void main(String... args) {
    try {
      Document document = Jsoup.connect("https://play.google.com/store").get();
      System.out.println(document.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
