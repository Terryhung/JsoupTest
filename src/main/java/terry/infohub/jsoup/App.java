package terry.infohub.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.safety.Whitelist;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import java.net.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception
    {
        Parsing();
    }

    public static void GetRule() throws Exception {
        String api_url = "https://api.droibaas.com/api/v2/infohub_task_handler";
        Map<String, String> data = new HashMap<>();
        data.put("task", "get-source-rule");
        data.put("_from", "news/msn");
        data.put("version", "100");

        String jsonStr = new ObjectMapper().writeValueAsString(data);

        System.out.println( jsonStr );

        Document doc = Jsoup.connect(api_url)
            .header("X-Droi-AppID", "of2umbzhoAcZ8_DnHF95NP-1SLhCdahYlQDIVxMA")
            .header("X-Droi-Api-Key", "0vTgWpqC8-ksol5rJb95oF0lUIYFa3PGQorsVXyuHWx7gR_5JpckMWcIIxH6wOPQ")
            .header("Content-Type", "application/json")
            .ignoreContentType(true)
            .requestBody(jsonStr)
            .post();
        System.out.println( doc );
    }

    public static String ParsingHost() throws Exception {
        String url = "http://www.cna.com.tw/news/aloc/201710030325-1.aspx";
        URL aURL = new URL(url);
        String host = aURL.getHost();
        return host;
    }

    public static void Parsing() throws Exception {
        String url = "http://www.cna.com.tw/news/aloc/201710030325-1.aspx";
        String image_url = "http://img5.cna.com.tw/www/WebPhotos/800/20171003/22066085.jpg";
        Document doc = Jsoup.connect(url)
            .header("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; SAMSUNG SM-G950U Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/5.2 Chrome/51.0.2704.106 Mobile Safari/537.36")
            .get();
        String title = doc.title();
        Elements divs = doc.select("div.news_article");
        Elements removed = doc.select("script, button");

        removed.remove();
        Element div = divs.first().prependElement("div").attr("style", "position: relative; width:100%; height: 200px");
        div.appendElement("img").attr("src", image_url);
        div.appendElement("h1").attr("style", "position: absolute; top: 200px; left: 0px; width:100%").text("title");

        System.out.println( divs );
    }
}
