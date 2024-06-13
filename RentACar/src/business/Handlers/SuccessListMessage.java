package business.Handlers;

import javax.swing.*;
import java.util.List;

public class SuccessListMessage<T> {
    private List<T> data;
    private String message;

    public SuccessListMessage(List<T> data, String message) {
        this.data = data;
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void showMessageDialog() {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
