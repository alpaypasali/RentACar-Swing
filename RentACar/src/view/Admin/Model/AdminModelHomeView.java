package view.Admin.Model;

import business.Concrete.ModelManager;
import business.Handlers.SuccessInformationMessage;
import business.Services.IModelService;
import entity.Model;
import entity.User;
import view.Admin.Brand.AdminCreateBrandView;
import view.AdminLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminModelHomeView extends AdminLayout {
    private JPanel container;
    private JScrollPane scrl_model;
    private JTable tbl_modelList;
    private JButton createButton;
    private User user;
    private JPopupMenu brandMenu;
    private IModelService modelService;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JPopupMenu modelMenu;


    public AdminModelHomeView(User user) {

        this.add(container);
        this.user = user;
        this.modelService = new ModelManager();
        this.brandMenu = new JPopupMenu();
        guiIntilaze();
        this.tbl_modelList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_modelList.rowAtPoint(e.getPoint());
                tbl_modelList.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    brandMenu.show(tbl_modelList, e.getX(), e.getY());
                }
            }
        });
        CreateTable();
        UpdateModel();
        CreateModel();
        DeleteBrand();
    }

    public void CreateTable() {
        Object[] columnNames = {"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};


        ArrayList<Object[]> userList = modelService.getForTable(columnNames.length, this.modelService.getAll());


        if (userList == null || userList.isEmpty()) {
            System.out.println("The table is empty. No data available.");

            userList = new ArrayList<>();
            userList.add(new Object[]{"No data", "", "", "", "", "", ""});
        }


        createTable(defaultTableModel, tbl_modelList, columnNames, userList);
    }

    public void UpdateModel() {

        this.brandMenu.add("Update").addActionListener(e -> {
            int selectedRow = tbl_modelList.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_modelList.getValueAt(selectedRow, 0).toString());
                Model selectedModel = this.modelService.getById(selectedId);
                AdminEditModelView editView = new AdminEditModelView(selectedModel, this);
                editView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable();
                    }
                });
            }
        });


    }

    public void CreateModel() {

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminCreateModelView modelView = new AdminCreateModelView();
                modelView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable();
                    }
                });

            }
        });


    }
    public void DeleteBrand(){

        this.brandMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_modelList.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_modelList.getValueAt(selectedRow, 0).toString());

                SuccessInformationMessage brandUpdated =  this.modelService.delete(selectedId);
                brandUpdated.showMessageDialog();
                CreateTable();

            }
        });


    }

}
