package app;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import searchAndTransform.FilesProvider;
import searchAndTransform.MatcherFactory;
import searchAndTransform.SearchAndRename;
import workerClasses.factories.CommonMatcherFactory;
import workerClasses.gui.FolderChooser;
import workerClasses.infrastructure.Modifier;

import java.security.Security;

/**
 * Created by Victor on 25/06/2016.
 */
public class Application {

    static final private String REGEX_AS_STRING = "(-\\s*)\\d{5}(\\s*-)";

    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());

        FilesProvider filesProvider = FolderChooser.newInstance();
        Modifier modifier = new Modifier();
        MatcherFactory matcherFactory = new CommonMatcherFactory();
        new SearchAndRename(REGEX_AS_STRING, modifier, filesProvider,matcherFactory).searchAndTransform();

    }
}
