package in.tanjo.httpintenter.model.license;

import static in.tanjo.httpintenter.model.license.License.Type.APACHE_2_0;
import static in.tanjo.httpintenter.model.license.License.Type.MECAB_IPADIC_NOTICE;
import static in.tanjo.httpintenter.model.license.License.Type.NO_LICENSE;

public class Licenses {

  public static final License butterKnife = new License.Builder()
      .libraryName("Butter Knife")
      .libraryUrl("https://github.com/JakeWharton/butterknife")
      .ownerName("Jake Wharton")
      .type(APACHE_2_0)
      .year("2013")
      .create();

  public static final License gson = new License.Builder()
      .libraryName("Gson")
      .libraryDescription("Gson is a Java library that can be used to convert Java Objects into their JSON representation.")
      .libraryUrl("https://github.com/google/gson")
      .ownerName("Google Inc.")
      .type(APACHE_2_0)
      .year("2008")
      .create();

  public static final License rxJava = new License.Builder()
      .libraryName("RxJava")
      .libraryDescription("Reactive Extensions for the JVM")
      .libraryUrl("https://github.com/ReactiveX/RxJava")
      .ownerName("RxJava Contributors.")
      .type(APACHE_2_0)
      .year("2016-present,")
      .create();

  public static final License rxBinding = new License.Builder()
      .libraryName("RxBinding")
      .libraryDescription("RxJava binding APIs for Android's UI widgets.\n")
      .libraryUrl("https://github.com/JakeWharton/RxBinding")
      .ownerName("Jake Wharton")
      .type(APACHE_2_0)
      .year("2015")
      .create();

  public static final License rxAndroid = new License.Builder()
      .libraryName("RxAndroid")
      .libraryUrl("https://github.com/ReactiveX/RxAndroid")
      .ownerName("The RxAndroid authors")
      .type(APACHE_2_0)
      .year("2015")
      .create();

  public static final License kuromoji = new License.Builder()
      .libraryName("Kuromoji Japanese Morphological Analyzer")
      .libraryUrl("https://github.com/atilika/kuromoji")
      .ownerName("Atilika Inc. and contributors (see CONTRIBUTORS.md)")
      .type(APACHE_2_0)
      .year("2010-2015")
      .create();

  public static final License kuromojiIpadic = new License.Builder()
      .libraryName("mecab-ipadic")
      .ownerName("Nara Institute of Science and Technology (NAIST)")
      .type(MECAB_IPADIC_NOTICE)
      .year("2007")
      .create();

  private static final License apache2 = new License.Builder()
      .libraryName("")
      .libraryUrl("")
      .ownerName("")
      .type(APACHE_2_0)
      .year("")
      .create();

  private static final License noLicense = new License.Builder()
      .libraryName("")
      .libraryUrl("")
      .ownerName("")
      .type(NO_LICENSE)
      .year("")
      .create();

  private Licenses() {
    // no instance.
  }
}
