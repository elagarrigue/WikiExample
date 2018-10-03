package example.wiki;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import example.wiki.service.WikipediaService;

public class MainActivity extends AppCompatActivity {

  private WikipediaService service = new WikipediaService();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    new Thread(new Runnable() {
      public void run() {

        Log.e("MainActivity", "Wiki says: " + service.getResult("cat"));

      }
    }).start();
  }

}
