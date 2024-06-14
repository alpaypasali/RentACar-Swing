package view.Admin.Model;

import business.Concrete.BrandManager;
import business.Concrete.ModelManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.ComboItem;
import business.Services.IBrandService;
import business.Services.IModelService;
import entity.Brand;
import entity.Model;
import entity.User;
import entity.enums.Fuel;
import entity.enums.Gear;
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
    private JComboBox cmb_brand;
    private JComboBox cmb_type;
    private JComboBox cmb_fuel;
    private JComboBox cmb_gear;
    private JButton searchButton;
    private User user;
    private JPopupMenu brandMenu;
    private IModelService modelService;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JPopupMenu modelMenu;
    private IBrandService brandService;
    Object[] columnNames;


    public AdminModelHomeView(User user) {

        this.add(container);
        this.user = user;
        this.modelService = new ModelManager();
        this.brandService = new BrandManager();
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
        CreateTable(null);
        UpdateModel();
        CreateModel();
        DeleteBrand();
        LoadFilter();
        GetFilter();
    }

    public void CreateTable(ArrayList<Object[]> modelList) {
        this.columnNames =new Object[] {"Model ID", "Marka", "Model Adı", "Tip", "Yıl", "Yakıt Türü", "Vites"};



        if (modelList == null || modelList.isEmpty()) {
            modelList = modelService.getForTable(this.columnNames.length, this.modelService.getAll());
            if (modelList == null || modelList.isEmpty()) {
                System.out.println("The table is empty. No data available.");
                modelList = new ArrayList<>();
                modelList.add(new Object[]{"No data", "", "", "", "", "", ""});
            }
        }






        createTable(defaultTableModel, tbl_modelList, columnNames, modelList);
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
                        CreateTable(null);
                        GetFilter();
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
                        CreateTable(null);
                        GetFilter();
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
                CreateTable(null);
                GetFilter();

            }
        });


    }
    public void LoadFilter(){

        this.cmb_type.setModel(new DefaultComboBoxModel<>(entity.enums.Type.values()));
        this.cmb_type.setSelectedItem(null);
        this.cmb_fuel.setModel(new DefaultComboBoxModel<>(Fuel.values()));
        this.cmb_fuel.setSelectedItem(null);
        this.cmb_gear.setModel(new DefaultComboBoxModel<>(Gear.values()));
        this.cmb_gear.setSelectedItem(null);
        cmb_brand.removeAllItems();
        for (Brand brand : this.brandService.getAll())
        {
            this.cmb_brand.addItem(new ComboItem(brand.getId(),brand.getName()));
        }

        this.cmb_brand.setSelectedItem(null);

    }

    public void GetFilter(){


        this.searchButton.addActionListener(e ->{

            ComboItem selectedBrand = (ComboItem) this.cmb_brand.getSelectedItem();
            ArrayList<Model> modelListBySearch = this.modelService.searchForTable(
                    selectedBrand.getKey(),
                    (Fuel) cmb_fuel.getSelectedItem(),
                    (Gear) cmb_gear.getSelectedItem(),
                    (entity.enums.Type) cmb_type.getSelectedItem()

            );
            ArrayList<Object[]> modelRowListBySearch = this.modelService.getForTable(this.columnNames.length,modelListBySearch);
            CreateTable(modelRowListBySearch);


        });
    }

}
