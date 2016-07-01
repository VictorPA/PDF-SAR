package workerClasses.notGuiGroup;


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
public abstract class FilePreparedForTreatment {

    protected File file;

    private FilePreparedForTreatment(File file) {
        this.file = file;
    }

    public static FilePreparedForTreatment getPreparedFileForTreatment(File file) {
        try {
            return new PdfPrepareForTreatement(file);
        } catch (IOException e) {
            return new NullPdfBox(file);
        }
    }



    public abstract String getFileParsedToString() throws IOException;
    public abstract void close() throws IOException;
    public abstract String getOutputName();

    public Path getPath() {
        return this.file.toPath();
    }




    private static class PdfPrepareForTreatement extends FilePreparedForTreatment {


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

        public void close() throws IOException {
            this.pdDoc.close();
            this.cosDoc.close();
            this.randomAccessFile.close();
        }

        @Override
        public String getOutputName() {
            return file.getName();
        }

    }

    private static class NullPdfBox extends FilePreparedForTreatment {

        private NullPdfBox(File file) {
            super(file);
        }

        @Override
        public String getFileParsedToString() throws IOException {
            return "";
        }

        @Override
        public String getOutputName() {
            return "";
        }

        @Override
        public void close() throws IOException {

        }
    }


}
