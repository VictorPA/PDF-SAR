package workerClasses.infrastructure;


import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Victor on 25/06/2016.
 */
public abstract class StandardizedFile {

    protected File file;

    private StandardizedFile(File file) {
        this.file = file;
    }

    public static StandardizedFile getPreparedFileForTreatment(File file) {
        try {
            return new PdfPrepareForTreatement(file);
        } catch (IOException e) {
            return new NullPdfBox(file);
        }
    }


    public abstract String getFileParsedToString() throws IOException;

    public abstract void dispose() throws IOException;

    public String getOutputName() {
        return file.getName();
    }

    public Path getPath() {
        return this.file.toPath();
    }


    private static class PdfPrepareForTreatement extends StandardizedFile {


        private PDFTextStripper pdfStripper;
        private RandomAccessFile randomAccessFile;
        private COSDocument cosDoc;
        private PDDocument pdDoc;

        private PdfPrepareForTreatement(File file) throws IOException {

            super(file);
            this.randomAccessFile = new RandomAccessFile(this.file, "r");
            PDFParser pdfParser = new PDFParser(this.randomAccessFile);
            pdfParser.parse();
            this.cosDoc = pdfParser.getDocument();
            this.pdDoc = new PDDocument(cosDoc);
            this.pdfStripper = new PDFTextStripper();
        }

        public String getFileParsedToString() throws IOException {
            return this.pdfStripper.getText(pdDoc);
        }

        public void dispose() throws IOException {
            this.pdDoc.close();
            this.cosDoc.close();
            this.randomAccessFile.close();
        }

    }

    private static class NullPdfBox extends StandardizedFile {

        private NullPdfBox(File file) {
            super(file);
        }

        @Override
        public String getFileParsedToString() throws IOException {
            return "";

        }

        @Override
        public void dispose() throws IOException {

        }
    }


}
