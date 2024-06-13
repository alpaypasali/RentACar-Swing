package business.Handlers;

import javax.swing.*;

public class SuccessInformationMessage {

    private String message;

    public SuccessInformationMessage(String message) {

        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void showMessageDialog() {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}