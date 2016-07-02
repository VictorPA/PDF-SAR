package workerClasses.infrastructure;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Victor on 20/06/2016.
 */
public class Modifier {

    /**
     * @param file         File that has been prepared
     * @param nameModifier
     * @return
     * @throws IOException
     */
    public void modifyFile(File file, String nameModifier) throws Exception {

        Path source = file.toPath();
        String oldFileName = file.getName();
        try {
            if (nameModifier.isEmpty()) {
                throw new Exception("The expected string to use for renaming file is empty");
            }
            if (!file.getName().contains(nameModifier)) {
                Path target = source.resolveSibling(nameModifier + "_" + file.getName());
                Files.move(source, target);
                System.out.println(oldFileName + " renamed to " + file.getName());
            }

        } catch (FileAlreadyExistsException e) {
            System.out.println("Ne peut transformer " + source.getFileName() + " en " + nameModifier +
                    "_" + file.getName() + " car le fichier existe déjà");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }



    }
}



