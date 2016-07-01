package highClasses;


import workerClasses.notGuiGroup.FileModifierEngine;
import workerClasses.notGuiGroup.FilePreparedForTreatment;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Victor on 23/06/2016.
 */
public class FileSearchAndRename {

    private static final String FILE_EXTENSION = ".pdf";
    private final FilesProvider filesProvider;
    private final FileModifierEngine fileModifierEngine;
    private final MatcherFactory matcherFactory;
    private String regex;
    private int totalFiles = 0;
    private int treatedFiles = 0;

    public FileSearchAndRename(String regex, FileModifierEngine fileModifierEngine, FilesProvider filesProvider, MatcherFactory matcherFactory) {
        this.matcherFactory = matcherFactory;
        this.filesProvider = filesProvider;
        this.fileModifierEngine = fileModifierEngine;
        this.regex = regex;
    }

    public void searchAndTransform() {

        List<File> fileList = filesProvider.getFiles();
        for (File file : fileList) {
            if (extensionIsValid(file)) {
                transform(file);
                ++totalFiles;
            }
        }
        System.out.println("Nombre de fichiers traités : " + treatedFiles + " sur " + totalFiles);
    }

    private void transform(File file) {
        try {
            FilePreparedForTreatment filePreparedForTreatment = FilePreparedForTreatment.getPreparedFileForTreatment(file);
            String fileAsString = fileToString(filePreparedForTreatment);
            String matchedText = getMatchedStringAfterApplyingSearchPattern(fileAsString);
            filePreparedForTreatment.close();
            if (!matchedText.isEmpty()) {
                File outputFile = fileModifierEngine.modifyFile(filePreparedForTreatment, matchedText);
                if (outputFile != null) {
                    System.out.println(file.getName() + " ==> " + outputFile.getName());
                    treatedFiles++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileToString(FilePreparedForTreatment filePreparedForTreatment) {
        String pdfAsString = null;
        try {
            pdfAsString = filePreparedForTreatment.getFileParsedToString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pdfAsString;
    }

    private String getMatchedStringAfterApplyingSearchPattern(String pdfAsString) {
        Matcher matcher = matcherFactory.createMatcher(regex);
        return matcher.getMatchedString(pdfAsString);
    }

    private boolean extensionIsValid(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.length() - FILE_EXTENSION.length());
        return fileExtension.contains(FILE_EXTENSION);

    }
}
