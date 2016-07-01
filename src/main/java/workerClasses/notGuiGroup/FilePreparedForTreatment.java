package workerClasses.notGuiGroup;


import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Victor on 25/06/2016.
 */
public class FilePreparedForTreatment {

    private PDFTextStripper pdfStripper;
    private RandomAccessFile randomAccessFile;
    private COSDocument cosDoc;
    private PDDocument pdDoc;
    private File file;


    public FilePreparedForTreatment(File file) {
        this.file = file;
    }

    public void init() {

        try {
            this.randomAccessFile = new RandomAccessFile(file, "r");
            PDFParser pdfParser = new PDFParser(this.randomAccessFile);
            pdfParser.parse();
            this.cosDoc = pdfParser.getDocument();
            this.pdDoc = new PDDocument(cosDoc);
            this.pdfStripper = new PDFTextStripper();
        } catch (IOException e) {

        }
    }


    public String getFileParsedToString() throws IOException {
        return this.pdfStripper.getText(pdDoc);
    }

    public void close() throws IOException {
        this.pdDoc.close();
        this.cosDoc.close();
        this.randomAccessFile.close();
    }

    public Path getPath() {
        return this.file.toPath();
    }

    public String getOutputName() {
        return this.file.getName();
    }

    private static class NullPdfBox extends FilePreparedForTreatment {

        private NullPdfBox(File file) {
            super(file);
        }

        @Override
        public String getFileParsedToString() throws IOException {
            return null;
        }

        @Override public Path getPath() {
            return null;
        }

        @Override public String getOutputName() {
            return null;
        }

        @Override public void close() throws IOException {

        }
    }
}
