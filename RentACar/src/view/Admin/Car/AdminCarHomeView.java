package view.Admin.Car;

import business.Concrete.CarManager;
import business.Services.ICarService;
import entity.Car;
import entity.User;
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
    private JComboBox cmb_brand;
    private JComboBox cmb_type;
    private JComboBox cmb_fuel;
    private JComboBox cmb_gear;
    private JButton searchButton;
    private  User user;
    private JPopupMenu carMenu;

    private ICarService carService;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();

    public AdminCarHomeView(User user){
            this.add(container);
            this.user = user;
            this.carService = new CarManager();
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
}
