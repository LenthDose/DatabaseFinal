package Tool;

import javax.swing.*;
import java.awt.*;

public class ChangeIconSize {

    public JButton ChangIconSize(JButton button, String url, int width, int height, String tip){
        button.setBounds(0,0,width,height);
        ImageIcon image = new ImageIcon(url);
        Image temp = image.getImage().getScaledInstance(button.getWidth(),button.getHeight(),image.getImage().SCALE_DEFAULT);
        button = new JButton(new ImageIcon(temp));
        button.setToolTipText(tip);
        return button;
    }
}
