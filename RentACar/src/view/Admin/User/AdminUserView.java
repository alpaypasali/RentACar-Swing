package view.Admin.User;

import business.Concrete.UserManager;
import business.Services.IUserService;
import entity.User;
import view.AdminLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AdminUserView extends AdminLayout {
    private JPanel container;
    private JLabel lbl_userTitle;
    private JTable tbl_userList;

    private User user;
    private IUserService userService;
    public DefaultTableModel defaultTableModel = new DefaultTableModel();

    public AdminUserView(User user) {
        this.user = user;
        userService = new UserManager();

        guiIntilaze();


        this.add(container);






        createTable();
    }

    public void createTable() {
        // Column names for the table
        Object[] columnNames = {"id", "name", "email", "phone", "role"};
        ArrayList<Object[]> userList = userService.getForTable(columnNames.length);


        createTable(defaultTableModel,tbl_userList,columnNames,userList);




    }
}

