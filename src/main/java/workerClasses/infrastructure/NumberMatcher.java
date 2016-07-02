package workerClasses.infrastructure;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Victor on 25/06/2016.
 */
public class NumberMatcher implements searchAndTransform.Matcher{

    private String regex;

    public NumberMatcher(String regex) {
        this.regex = regex;
    }

    public String getMatchedString(String fileAsString) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = null;
        if (fileAsString != null) {
            matcher = pattern.matcher(fileAsString);
        }
        return extractMatchedPattern(matcher);
    }

    private String extractMatchedPattern(Matcher matcher) {
        String rawMatchedText;
        String filteredMatchedText = "";
        if (matcher.find()) {
            rawMatchedText = matcher.group();
            for (int c = 0; c < rawMatchedText.length(); ++c) {
                if (Character.isDigit(rawMatchedText.charAt(c)))
                    filteredMatchedText += rawMatchedText.charAt(c);
            }
        }
        return filteredMatchedText;
    }
}
