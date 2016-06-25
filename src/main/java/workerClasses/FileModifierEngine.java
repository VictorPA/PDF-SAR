package workerClasses;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Victor on 20/06/2016.
 */
public class FileModifierEngine {


    public File modifyFile(ProcessedFile processedFile, String filteredMatchedText) throws IOException {

        Path source = processedFile.getPath();
        File finalFile = null;

        try {
            if (!processedFile.getName().contains(filteredMatchedText)) {
                Path target = source.resolveSibling(filteredMatchedText + "_" + processedFile.getName());
                Files.move(source, target);
                finalFile = target.toFile();
            }

        } catch (FileAlreadyExistsException e) {
            System.out.println("Ne peut transformer " + source.getFileName() + " en " + filteredMatchedText +
                    "_" + processedFile.getName() + " car le fichier existe déjà");
        }

        return finalFile;

    }
}



