package view.Admin;

import business.Helpers.FrameHelper;
import entity.User;
import view.Admin.Brand.AdminBrandHomeView;
import view.Admin.Car.AdminCarHomeView;
import view.Admin.Model.AdminModelHomeView;
import view.Admin.User.AdminUserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomeView extends JFrame {
    private JPanel container;
    private JButton btn_mngBrands;
    private JButton btn_mngUsers;
    private JButton btn_mngModels;
    private JButton btn_mngBooks;
    private JButton btn_mngCars;
    private User user;

    public  AdminHomeView(User user){
        this.add(container);
        this.user = user;
        FrameHelper.setupFrame(this, 500, 250, "Rent A Car");


        btn_mngUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminUserView userView = new AdminUserView(user);

            }
        });
        btn_mngBrands.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminBrandHomeView brandHomeView = new AdminBrandHomeView(user);
            }
        });
        btn_mngModels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminModelHomeView modelHomeView = new AdminModelHomeView(user);
            }
        });
        btn_mngCars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminCarHomeView carHomeView = new AdminCarHomeView(user);
            }
        });
    }


}

