package view.Admin.Brand;


import business.Concrete.BrandManager;
import business.Handlers.SuccessInformationMessage;
import business.Services.IBrandService;
import entity.Brand;
import entity.User;
import view.AdminLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminBrandHomeView extends AdminLayout {
    private User user;
    private JPanel container;
    private JScrollPane scrl_brand;
    private JTable tbl_brandList;
    private JButton createButton;
    private IBrandService brandService;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private  JPopupMenu brandMenu;


    public AdminBrandHomeView(User user) {
        this.add(container);
        this.user = user;
        this.brandService = new BrandManager();
        this.brandMenu = new JPopupMenu();
        guiIntilaze();
        CreateTable();
        this.tbl_brandList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_brandList.rowAtPoint(e.getPoint());
                tbl_brandList.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    brandMenu.show(tbl_brandList, e.getX(), e.getY());
                }
            }
        });
        UpdateBrand();
        DeleteBrand();
        CreateBrand();



    }
    public void UpdateBrand(){

        this.brandMenu.add("Update").addActionListener(e -> {
            int selectedRow = tbl_brandList.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_brandList.getValueAt(selectedRow, 0).toString());
                Brand selectedUser = this.brandService.getById(selectedId);
                AdminEditBrandView editView = new AdminEditBrandView(selectedUser, this);
                editView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable();
                    }
                });
            }
        });


    }
    public void CreateTable(){
        Object[] columnNames = {"id", "name"};
        ArrayList<Object[]> userList = brandService.getForTable(columnNames.length);
        createTable(defaultTableModel,tbl_brandList,columnNames,userList);
    }
    public void DeleteBrand(){

        this.brandMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_brandList.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_brandList.getValueAt(selectedRow, 0).toString());

                SuccessInformationMessage brandUpdated =  this.brandService.delete(selectedId);
                brandUpdated.showMessageDialog();
                CreateTable();

            }
        });


    }
    public void CreateBrand(){

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminCreateBrandView brandView = new AdminCreateBrandView(this);
                brandView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable();
                    }
                });

            }
        });



    }


}