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
    private List<String> articleTitles;
    private String url;

    public Parser(String url) {
        this.url=url;
        urlList=new ArrayList<>();
        articleTitles=new ArrayList<>();
    }
    public List<String> parseArticleTitles(){
        parseUrlList();
        for (String page:urlList
             ) {

            articleTitles.addAll(recursiveParsing(page));
        }
        return articleTitles;
    }
    private void parseUrlList()
    {
        int numPages=getMaxPage(url);
        //Adding reference for the first page
        urlList.add("https://habr.com/ru/page");
        //Adding references for rest pages
        for (int i = 2; i <= numPages ; i++) {
            urlList.add("https://habr.com/ru/page"+i);
        }
    }
    private int getMaxPage(String url)
    {
        List<String> result=new ArrayList<>();
        result=referenceParsing(url);
        return PageNumberObtainer.obtainFromUrl(result.get(result.size()-1));
    }
    private List<String> referenceParsing(String url)
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
    private List<String> recursiveParsing(String url)
    {
        List<String> result=new ArrayList<>();

        try {
            Document document=Jsoup.connect(url).get();
            result=document.getElementsByClass("post__title").stream().map(el->el.getElementsByTag("a").text()).collect(Collectors.toList());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
