package workerClasses.notGuiGroup;

/**
 * Created by Victor on 29/06/2016.
 */
public class FileNotTreatableException extends Exception {

    private String problemFile;

    public FileNotTreatableException(String message, String problemFile) {
        super(message);
    }

    public String getproblemFile() {
        return problemFile;
    }

    @Override
    public void printStackTrace() {
        System.err.println("Fichier problématique : " + problemFile + ".");
        super.printStackTrace();
    }
}
