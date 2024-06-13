package view.User.Login;

import business.Concrete.UserManager;
import business.Handlers.SuccessMessage;
import business.Helpers.FrameHelper;
import business.Services.IUserService;
import entity.User;
import view.Admin.AdminHomeView;
import view.User.Register.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JPanel container;
    private JTextField txt_email;
    private JPasswordField txt_password;
    private JButton btn_login;
    private JButton btn_signUp;
    private JLabel lbl_signUp;
    private IUserService userService;

    public LoginView(){
        userService = new UserManager();
        this.add(container);
        FrameHelper.setupFrame(this, 400, 400, "Rent A Car");

        btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuccessMessage<User> user = userService.signIn(txt_email.getText(), txt_password.getText());
                if (user != null) {
                    user.showMessageDialog();
                    if(user.getData().getRole().equals("admin")){
                        AdminHomeView adminHomeView = new AdminHomeView(user.getData());

                    }
                    dispose();
                }


            }
        });
        btn_signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView registerView = new RegisterView(new User());
            }
        });
    }



}