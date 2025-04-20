package Recursos.PersonalClassF;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class ProgressDialog extends JDialog {
    private JLabel messageLabel;

    public ProgressDialog(String title, String message) {
        setTitle(title);
        setSize(300, 100);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        messageLabel = new JLabel(message);
        add(messageLabel);
    }

    public void showProgress() {
        setVisible(true);
    }

    public void hideProgress() {
        setVisible(false);
    }
}
