package Manager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileOpen extends JFrame{

    public FileOpen() throws IOException {
        JFileChooser jf = new JFileChooser("word");
        jf.showOpenDialog(this);
        File f = jf.getSelectedFile();
        String s = f.getAbsolutePath();
        Desktop.getDesktop().open(new File(s));
    }

}
