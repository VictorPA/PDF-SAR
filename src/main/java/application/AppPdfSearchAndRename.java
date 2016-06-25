package application;

import pdfParser.PdfMofifierEngine;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Victor on 23/06/2016.
 */
public class AppPdfSearchAndRename {

    private static String regex = "(-\\s*)\\d{5}(\\s*-)";
    private static int totalFiles = 0;
    private static int treatedFiles = 0;


    public static void main(String args[]) {

        PdfMofifierEngine pdfMofifierEngine = new PdfMofifierEngine();
        String chosenPdfsFolder = PdfMofifierEngine.getFolderWithPdfsChooser().choose();
        if(chosenPdfsFolder == null)
            return;
        File[] files = new File(chosenPdfsFolder).listFiles();
        PdfMofifierEngine.ProcessedPdfFile processedPdfFile = null;
        String parsedText = null;
        if (files != null) {
            for (File file : files) {
                try {
                    if (file.isFile()) {
                        processedPdfFile = new PdfMofifierEngine.ProcessedPdfFile(file);
                        parsedText = processedPdfFile.getParsedText();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher;
                if (parsedText != null) {
                    matcher = pattern.matcher(parsedText);
                } else
                    continue;
                String filteredMatchedText = "";
                filteredMatchedText = pdfMofifierEngine.extractMatchedText(matcher);
                System.out.println(filteredMatchedText);
                try {
                    pdfMofifierEngine.modifyPdf(processedPdfFile, filteredMatchedText);
                    treatedFiles++;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Nombre de fichiers traités : " + treatedFiles);
        }


    }
}
