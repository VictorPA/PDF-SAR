package workerClasses.notGuiGroup;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Victor on 20/06/2016.
 */
public class FileModifierEngine {


    public File modifyFile(FilePreparedForTreatment filePreparedForTreatment, String filteredMatchedText) throws IOException {

        Path source = filePreparedForTreatment.getPath();
        File finalFile = null;

        try {
            if (!filteredMatchedText.isEmpty() && !filePreparedForTreatment.getOutputName().contains(filteredMatchedText)) {
                Path target = source.resolveSibling(filteredMatchedText + "_" + filePreparedForTreatment.getOutputName());
                Files.move(source, target);
                finalFile = target.toFile();
            }

        } catch (FileAlreadyExistsException e) {
            System.out.println("Ne peut transformer " + source.getFileName() + " en " + filteredMatchedText +
                    "_" + filePreparedForTreatment.getOutputName() + " car le fichier existe déjà");
        }catch(Exception e){
            e.printStackTrace();
        }

        return finalFile;

    }
}



