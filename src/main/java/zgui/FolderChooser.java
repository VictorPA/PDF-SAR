package zgui;

import javax.swing.*;
import java.io.File;

/**
 * Created by Victor on 23/06/2016.
 */


public class FolderChooser {

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

    public static FolderChooser newInstance() {
        return new FolderChooser();
    }

    public File choose() {
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