import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import gui.generalGUI.mainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        FlatLightLaf.setup();
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            System.out.println(info.getClassName());
        }
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        mainFrame mainFrame=new mainFrame();

    }
}
