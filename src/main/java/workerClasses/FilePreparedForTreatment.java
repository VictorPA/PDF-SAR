package workerClasses;

import highClasses.FileNotTreatableException;
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
public class FilePreparedForTreatment {

    private PDFTextStripper pdfStripper;
    private String outputName;
    private Path path;
    private RandomAccessFile randomAccessFile;
    private COSDocument cosDoc;
    private PDDocument pdDoc;
    private boolean isClosed;

    public FilePreparedForTreatment(File file) throws FileNotTreatableException {

        try {
            this.outputName = file.getName();
            this.path = file.toPath();
            this.randomAccessFile = new RandomAccessFile(file, "r");
            PDFParser pdfParser = new PDFParser(this.randomAccessFile);
            pdfParser.parse();
            this.cosDoc = pdfParser.getDocument();
            this.pdDoc = new PDDocument(cosDoc);
            this.pdfStripper = new PDFTextStripper();
            this.isClosed = false;
        } catch (IOException e) {
            throw new FileNotTreatableException("Erreur lors de la préparation du fichier", outputName);
        }

    }

    public String getFileParsedToString() throws Exception {
        return this.pdfStripper.getText(pdDoc);
    }

    public void close() throws IOException {
        this.pdDoc.close();
        this.cosDoc.close();
        this.randomAccessFile.close();
        this.isClosed = true;
    }

    public boolean isClosed(){
        return this.isClosed;
    }

    public Path getPath() {
        return this.path;
    }

    public String getOutputName() {
        return this.outputName;
    }
}
