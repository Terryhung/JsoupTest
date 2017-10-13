package terry.infohub.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.safety.Whitelist;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import org.codehaus.jackson.map.ObjectMapper;
import java.net.*;
import java.nio.file.*;

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

    public static void CreateHTML(List<String> content) throws Exception {
        Path file = Paths.get("Jsoup_test.html");
        Files.write(file, content);
    }

    public static void AddDocHead(Elements doc) throws Exception {
        Element head = doc.first();
        head.prepend("<style>.article-header{content: '';background: linear-gradient(to top, #000 1rem,#fff 40rem,#fff 100%);height: 35rem;display: block;}.main-image{width: 100%;max-height: 700px;opacty: 0.9}.article-content{background-color: #fff;position: absolute;width: 90%;z-index: 1;margin-left: 4%; margin-right: 5%;margin-top: -2rem;font-size: 25px; padding-left: 5px; padding-right: 5px}.article-header:after {content: '';background: #000;background: -moz-linear-gradient(top, #000 28%, #fff 58%, #fff 100%);background: -webkit-linear-gradient(top, #000 22rem,#fff 40rem,#fff 100%);background: linear-gradient(to bottom, #000 22rem,#fff 40rem,#fff 100%);height: 40rem;display: block;} .article-content > img{width: 100%}</style>");
    }

    public static void Parsing() throws Exception {
        String url = "http://www.cna.com.tw/news/aloc/201710030325-1.aspx";
        Document doc = Jsoup.connect(url)
            .header("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; SAMSUNG SM-G950U Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/5.2 Chrome/51.0.2704.106 Mobile Safari/537.36")
            .get();

        String target = "div.news_article";
        Elements divs = doc.select(target);
        String remove = "script, button";
        Elements removed = doc.select(remove);
        String image_url = "http://img.appledaily.com.tw/images/twapple/640pix/20170925/BN02/BN02_005.jpg";
        String news_title = "苗栗縣鼓勵青年創業苗栗縣鼓勵青年創業苗栗縣鼓勵青年創業";
        removed.remove();

        /* Add Title to first position*/
        divs.first().prependElement("h2").text(news_title);

        /*Wrap New Tag*/
        String child_html = divs.html();
        divs.html(String.format("<div class='article-content'>%s<div>", child_html));
        System.out.println(divs);
        divs.prepend(String.format("<div class='article-header'><img src='%s' class='main-image'></div></div>", image_url));

        AddDocHead(divs);
        CreateHTML(Arrays.asList(divs.html()));
    }
}
