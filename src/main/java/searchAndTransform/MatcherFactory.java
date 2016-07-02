package searchAndTransform;

/**
 * Created by Victor on 25/06/2016.
 */
public interface MatcherFactory {
   Matcher createMatcher(String regex);
}
