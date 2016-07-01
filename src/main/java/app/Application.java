package app;

import highClasses.FileSearchAndRename;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import utility.CommonMatcherFactory;
import utility.MatcherFactory;
import workerClasses.FileModifierEngine;
import zgui.FilesProvider;
import zgui.FolderChooser;

import java.security.Security;

/**
 * Created by Victor on 25/06/2016.
 */
public class Application {

    static final private String REGEX_AS_STRING = "(-\\s*)\\d{5}(\\s*-)";

    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());

        FilesProvider filesProvider = FolderChooser.newInstance();
        FileModifierEngine fileModifierEngine = new FileModifierEngine();
        MatcherFactory matcherFactory = new CommonMatcherFactory();
        new FileSearchAndRename(REGEX_AS_STRING, fileModifierEngine, filesProvider,matcherFactory).searchAndTransform();

    }
}
