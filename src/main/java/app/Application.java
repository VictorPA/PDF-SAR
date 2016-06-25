package app;

import highClasses.FileSearchAndRename;
import utility.CommonMatcherFactory;
import utility.MatcherFactory;
import workerClasses.FileModifierEngine;
import zgui.FolderChooser;

/**
 * Created by Victor on 25/06/2016.
 */
public class Application {

    static final private String REGEX_AS_STRING = "(-\\s*)\\d{5}(\\s*-)";

    public static void main(String[] args) {

        FolderChooser folderChooser = FolderChooser.newInstance();
        FileModifierEngine fileModifierEngine = new FileModifierEngine();
        MatcherFactory matcherFactory = new CommonMatcherFactory();
        new FileSearchAndRename(REGEX_AS_STRING, fileModifierEngine, folderChooser,matcherFactory).doSearchAndRename();

    }
}
