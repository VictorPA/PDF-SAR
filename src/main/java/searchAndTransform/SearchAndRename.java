package searchAndTransform;


import workerClasses.infrastructure.Modifier;
import workerClasses.infrastructure.StandardizedFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Victor on 23/06/2016.
 */
public class SearchAndRename {

    private static final String FILE_EXTENSION = ".pdf";
    private final FilesProvider filesProvider;
    private final Modifier modifier;
    private final MatcherFactory matcherFactory;
    private String regex;
    private int totalFiles = 0;
    private int treatedFiles = 0;

    public SearchAndRename(String regex, Modifier modifier, FilesProvider filesProvider, MatcherFactory matcherFactory) {
        this.matcherFactory = matcherFactory;
        this.filesProvider = filesProvider;
        this.modifier = modifier;
        this.regex = regex;
    }

    public void searchAndTransform() {
        List<File> fileList = filesProvider.getFiles();
        for (File file : fileList) {
            if (extensionIsValid(file)) {
                transform(file);
                ++this.totalFiles;
            }
        }
        System.out.println("Nombre de fichiers traités : " + treatedFiles + " sur " + totalFiles);
    }

    private void transform(File file) {
        try {
            String extractedText = convertToStringThe(file);
            String matchedText = getMatchedStringAfterApplyingSearchPatternFrom(extractedText);
            modifyFile(file, matchedText);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Le fichier " + file.getName() + " a échoué à être traité");
        }

    }

    private String convertToStringThe(File file) throws IOException {
        StandardizedFile standardizedFile = StandardizedFile.getPreparedFileForTreatment(file);
        String extractedText = convertToStringThe(standardizedFile);
        try {
            standardizedFile.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractedText;
    }

    private String convertToStringThe(StandardizedFile standardizedFile) throws IOException {
        return standardizedFile.getFileParsedToString();
    }

    private String getMatchedStringAfterApplyingSearchPatternFrom(String pdfAsString) {
        Matcher matcher = matcherFactory.createMatcher(regex);
        return matcher.getMatchedString(pdfAsString);
    }

    private void modifyFile(File file, String matchedText) {

        try {
            this.modifier.modifyFile(file, matchedText);
            this.treatedFiles++;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Le fichier "+ file.getName() +" n'a pu être modifié");
        }
    }

    private boolean extensionIsValid(File file) {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.length() - FILE_EXTENSION.length());
        return fileExtension.contains(FILE_EXTENSION);

    }
}
