package jogogourmet.utils;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConfirmeDialog {

    static JPanel panel = new JPanel();

    public static Object show(String msg, String tit, boolean input, int tpomsg , String... options) {

        JDialog.setDefaultLookAndFeelDecorated(true);

        if (!input) {
            return JOptionPane.showOptionDialog(panel, msg, tit, JOptionPane.YES_NO_OPTION,
            tpomsg, null, options, options[0]);
        } else {
            String response;
            do {
                response = JOptionPane.showInputDialog(panel, msg, tit, 3);
             } while (response != null && response.equals(""));
            return response;
        }
       
    }
}
