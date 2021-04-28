package habr;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    private Document document;
    private List<String> urlList;
    private String url;
    public Parser(String url) {
        this.url=url;
        urlList=new ArrayList<>();
    }

    public void parseUrlList()
    {
        int numPages=getMaxPage(url);
        for (int i = 2; i <= numPages ; i++) {
            System.out.println("https://habr.com/ru/page"+i);
        }
    }
    private int getMaxPage(String url)
    {
        List<String> result=new ArrayList<>();
        result=recursiveParsing(url);
        return PageNumberObtainer.obtainFromUrl(result.get(result.size()-1));
    }
    private List<String> recursiveParsing(String url)
    {
        List<String> result=new ArrayList<>();

        try {
            Document document=Jsoup.connect(url).get();
            result=document.getElementsByClass("toggle-menu__item toggle-menu__item_pagination").stream().map(el->el.getElementsByTag("a")).collect(Collectors.toList()).stream().map(element -> element.attr("href")).collect(Collectors.toList());

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
