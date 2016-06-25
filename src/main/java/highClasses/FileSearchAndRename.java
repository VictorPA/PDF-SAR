package highClasses;


import utility.MatcherFactory;
import workerClasses.FileModifierEngine;
import workerClasses.ProcessedFile;
import zgui.FolderChooser;

import java.io.File;
import java.io.IOException;

/**
 * Created by Victor on 23/06/2016.
 */
public class FileSearchAndRename {

    private static final String FILE_EXTENSION = ".pdf";

    private final FolderChooser folderChooser;
    private final FileModifierEngine fileModifierEngine;
    private final MatcherFactory matcherFactory;
    private String regex;
    private int totalFiles = 0;
    private int treatedFiles = 0;

    public FileSearchAndRename(String regex, FileModifierEngine fileModifierEngine, FolderChooser folderChooser, MatcherFactory matcherFactory) {
        this.matcherFactory = matcherFactory;
        this.folderChooser = folderChooser;
        this.fileModifierEngine = fileModifierEngine;
        this.regex = regex;
    }

    public void doSearchAndRename() {

        File chosenFolder = folderChooser.choose();
        if (chosenFolder != null && chosenFolder.isDirectory()) {
            File[] files = chosenFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (extensionIsValid(file)) {
                        treat(file);
                        ++totalFiles;
                    }

                }
                System.out.println("Nombre de fichiers traités : " + treatedFiles + " sur " + totalFiles);
            }
        } else
            System.out.println("Annulation");
    }

    private void treat(File file) {

        ProcessedFile processedFile = process(file);
        String fileAsString = processedFileToText(processedFile);
        String filteredMatchedText = match(fileAsString);

        try {
            processedFile.close();
            File finalFile = fileModifierEngine.modifyFile(processedFile, filteredMatchedText);
            if (finalFile != null) {
                System.out.println(file.getName() + " ==> " + finalFile.getName());
                treatedFiles++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProcessedFile process(File file) {
        ProcessedFile processedFile = null;
        if (file.isFile()) {
            try {
                processedFile = new ProcessedFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return processedFile;
    }

    private String processedFileToText(ProcessedFile processedFile) {
        String pdfAsString = null;
        try {
            pdfAsString = processedFile.getFileParsedToText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfAsString;
    }

    private String match(String pdfAsString) {
        Matcher matcher = matcherFactory.createMatcher(regex);
        return matcher.getMatchedString(pdfAsString);
    }

    private boolean extensionIsValid(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.length() - FILE_EXTENSION.length());
        return fileExtension.equals(FILE_EXTENSION);

    }
}
