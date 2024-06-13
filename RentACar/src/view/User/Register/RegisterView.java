package view.User.Register;

import business.Concrete.UserManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.FrameHelper;
import business.Services.IUserService;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_name;
    private JTextField fld_name;
    private JTextField fld_email;
    private JTextField fld_phone;
    private User user;
    private JPasswordField fld_password;
    private JButton registerButton;
    private IUserService userService;

    public RegisterView(User user){
        this.add(container);
        FrameHelper.setupFrame(this, 250, 400, "Rent A Car");
        userService = new UserManager();
        this.user = user;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setEmail(fld_email.getText());
                user.setName(fld_name.getText());
                user.setPassword(fld_password.getText());
                user.setPhone(fld_phone.getText());

                SuccessInformationMessage createdUser = userService.create(user);
                createdUser.showMessageDialog();
                dispose();
            }
        });
    }
}
