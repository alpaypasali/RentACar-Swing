package view.Admin.Car;

import business.Concrete.BrandManager;
import business.Concrete.CarManager;
import business.Concrete.ModelManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.ComboItem;
import business.Services.IBrandService;
import business.Services.ICarService;
import business.Services.IModelService;
import entity.Brand;
import entity.Car;
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

public class AdminCarHomeView extends AdminLayout {
    private JPanel container;
    private JScrollPane scrl_car;
    private JTable tbl_carList;
    private JButton createButton;
    private  User user;
    private JPopupMenu carMenu;

    private ICarService carService;
    private IModelService modelService;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();

    public AdminCarHomeView(User user){
            this.add(container);
            this.user = user;
            this.carService = new CarManager();
            this.modelService = new ModelManager();
        this.carMenu = new JPopupMenu();
        this.tbl_carList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_carList.rowAtPoint(e.getPoint());
                tbl_carList.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    carMenu.show(tbl_carList, e.getX(), e.getY());
                }
            }
        });
            guiIntilaze();

            CreateTable();
            CreateCar();
            UpdateCar();
            DeleteCar();

    }

    public void CreateTable(){
        Object[] columnNames = {"ID", "Brand", "Model", "Plate", "Color", "KM", "Year" , "Type" , "Fuel" , "Gear"};


        ArrayList<Object[]> carList = carService.getForTable(columnNames.length, this.carService.getAll());


        if (carList == null || carList.isEmpty()) {
            System.out.println("The table is empty. No data available.");

            carList = new ArrayList<>();
            carList.add(new Object[]{"No data"});
        }


        createTable(defaultTableModel, tbl_carList, columnNames, carList);
    }
    public void CreateCar() {

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminCreateCarView createCarView = new AdminCreateCarView();
                createCarView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable();
                    }
                });

            }
        });


    }
    public void UpdateCar() {

        this.carMenu.add("Update").addActionListener(e -> {
            int selectedRow = tbl_carList.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_carList.getValueAt(selectedRow, 0).toString());
                Car selectedModel = this.carService.getById(selectedId);
                AdminUpdateCarView editView = new AdminUpdateCarView(selectedModel, this);
                editView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable();

                    }
                });
            }
        });


    }
    public void DeleteCar(){

        this.carMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_carList.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_carList.getValueAt(selectedRow, 0).toString());

                SuccessInformationMessage brandUpdated =  this.modelService.delete(selectedId);
                brandUpdated.showMessageDialog();
                CreateTable();


            }
        });


    }

}
