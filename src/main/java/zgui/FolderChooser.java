package zgui;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Victor on 23/06/2016.
 */


public class FolderChooser implements FilesProvider {


    private final JFileChooser chooser;

    private FolderChooser() {
        setLookAndFeel();
        chooser = new JFileChooser();
        configureChooser();
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void configureChooser() {
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Sélectionner le dossier contenant les pdfs");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

    }

    public static FilesProvider newInstance() {
        return new FolderChooser();
    }

    @Override
    public List<File> getFiles() {
        File chosenFolder = chooseFolderWithGui();
        List<File> fileList;
        if (chosenFolder != null && chosenFolder.isDirectory()) {
            File[] arrayOfFiles = chosenFolder.listFiles();
            if (arrayOfFiles != null) {
                fileList = Arrays.asList(arrayOfFiles);
            } else
                fileList = Collections.emptyList();
        } else
            fileList = Collections.emptyList();

        return fileList;
    }

    private File chooseFolderWithGui() {
        int dialogIssue = chooser.showOpenDialog(null);
        File folder;
        switch (dialogIssue) {
            case JFileChooser.APPROVE_OPTION:
                folder = chooser.getSelectedFile();
                break;
            default:
                folder = null;
        }
        return folder;

    }


}