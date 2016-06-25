package pdfParser;

import org.springframework.context.annotation.PropertySource;

import javax.swing.*;

/**
 * Created by Victor on 23/06/2016.
 */


public class FolderWithPdfsChooser {

    private JFileChooser chooser;


    private FolderWithPdfsChooser() {
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
        chooser.setDragEnabled(true);
    }

    static FolderWithPdfsChooser newInstance() {
        return new FolderWithPdfsChooser();
    }

    public String choose() {

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().toString();
        } else {
            return null;
        }

    }


}
