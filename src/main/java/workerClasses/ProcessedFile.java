package workerClasses;

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
public class ProcessedFile {

    private final PDFTextStripper pdfStripper;
    private String name;
    private Path path;
    private RandomAccessFile randomAccessFile;
    private COSDocument cosDoc;
    private PDDocument pdDoc;
    private boolean isClosed;

    public ProcessedFile(File file) throws IOException {
        this.name = file.getName();
        this.path = file.toPath();
        this.randomAccessFile = new RandomAccessFile(file, "r");
        PDFParser pdfParser = new PDFParser(this.randomAccessFile);
        pdfParser.parse();
        this.cosDoc = pdfParser.getDocument();
        this.pdDoc = new PDDocument(cosDoc);
        this.pdfStripper = new PDFTextStripper();
        this.isClosed = false;

    }

    public String getFileParsedToText() throws IOException {
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

    public String getName() {
        return this.name;
    }
}
