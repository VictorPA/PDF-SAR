package workerClasses.factories;

import searchAndTransform.Matcher;
import searchAndTransform.MatcherFactory;
import workerClasses.infrastructure.NumberMatcher;

/**
 * Created by Victor on 25/06/2016.
 */
public class CommonMatcherFactory implements MatcherFactory {

    public Matcher createMatcher(String regex){
            return new NumberMatcher(regex);
    }
}
