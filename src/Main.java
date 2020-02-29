import UI.LoginFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new LoginFrame();
    }
}
