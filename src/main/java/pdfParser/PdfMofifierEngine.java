package pdfParser;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * Created by Victor on 20/06/2016.
 */
public class PdfMofifierEngine {

    private static FolderWithPdfsChooser folderWithPdfsChooser;

    static {
        folderWithPdfsChooser = FolderWithPdfsChooser.newInstance();
    }

    public static FolderWithPdfsChooser getFolderWithPdfsChooser() {
        return folderWithPdfsChooser;
    }

    public String extractMatchedText(Matcher matcher) {
        String rawMatchedText = "";
        String filteredMatchedText = "";
        if (matcher.find()) {
            rawMatchedText = matcher.group(); // retrieve the matched substring
            for (int c = 0; c < rawMatchedText.length(); ++c) {
                if (Character.isDigit(rawMatchedText.charAt(c)))
                    filteredMatchedText += rawMatchedText.charAt(c);
            }
        } else
            filteredMatchedText = null; // no match found
        return filteredMatchedText;
    }

    public void modifyPdf(ProcessedPdfFile processedPdfFile, String filteredMatchedText) throws IOException {
        Path source = processedPdfFile.path;
        processedPdfFile.close();
        Files.move(source, source.resolveSibling(filteredMatchedText + " " + processedPdfFile.name));

    }


    public static class ProcessedPdfFile {
        private final PDFTextStripper pdfStripper;
        String name;
        Path path;
        RandomAccessFile randomAccessFile;
        COSDocument cosDoc;
        PDDocument pdDoc;
        PDFParser pdfParser;

        public ProcessedPdfFile(File file) throws IOException {
            this.name = file.getName();
            this.path = file.toPath();
            this.randomAccessFile = new RandomAccessFile(file, "r");
            this.pdfParser = new PDFParser(this.randomAccessFile);
            pdfParser.parse();
            cosDoc = pdfParser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);

        }

        public String getParsedText() throws IOException {
            return pdfStripper.getText(pdDoc);
        }

        private void close() throws IOException {
            pdDoc.close();
            cosDoc.close();
            randomAccessFile.close();
        }

    }


}
