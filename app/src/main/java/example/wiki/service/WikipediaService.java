package example.wiki.service;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikipediaService {

  private Retrofit retrofit = new Retrofit.Builder()
      .baseUrl("https://en.wikipedia.org/w/")
      .addConverterFactory(ScalarsConverterFactory.create())
      .build();

  private final WikipediaAPI wikiAPI = retrofit.create(WikipediaAPI.class);

  public String getResult(String topic) {
    String text = null;

    Response<String> callResponse;
    try {
      callResponse = wikiAPI.getTerm(topic).execute();

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(new InputSource(new java.io.StringReader(callResponse.body())));

      NodeList nodes = doc.getDocumentElement().getElementsByTagName("extract");

      Node extract = nodes.item(0);

      if (extract == null) {
        text = "No Results";
      } else {
        text = extract.getTextContent().replace("\\n", "\n");

      }

    } catch (IOException | ParserConfigurationException | SAXException e1) {
      e1.printStackTrace();
    }

    return text;
  }
}
