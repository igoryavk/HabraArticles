package habr;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageNumberObtainer {
    private PageNumberObtainer() {

    }
    public static int obtainFromUrl(String url)
    {

        Pattern pattern= Pattern.compile("[//]");
        List<String> splitedStrings= Arrays.asList(pattern.split(url).clone());
        pattern=Pattern.compile("page");
        Matcher matcher=pattern.matcher(splitedStrings.get(splitedStrings.size()-1));
        String result=matcher.replaceAll("");
        return new Integer(result);
    }
}
