package utility;

import highClasses.Matcher;

/**
 * Created by Victor on 25/06/2016.
 */
public class CommonMatcherFactory implements MatcherFactory{

    public Matcher createMatcher(String regex){
            return new NumberMatcher(regex);
    }
}
