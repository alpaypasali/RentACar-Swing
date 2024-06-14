package view.Home;

import business.Concrete.CarManager;
import business.Helpers.FrameHelper;
import business.Services.ICarService;
import entity.Car;
import entity.User;
import entity.enums.Fuel;
import entity.enums.Gear;
import view.Admin.User.Book.UserBookView;
import view.Admin.User.Book.UserCreateReservationView;
import view.AdminLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeView extends AdminLayout {

    private JPanel container;
    private JComboBox<Fuel> cmb_fuel;
    private JComboBox<Gear> cmb_gear;
    private JTextField txt_startDate;
    private JTextField txt_finishDate;
    private JComboBox<entity.enums.Type> cmb_type;
    private JButton searchCarButton;
    private JTable bookTable;
    private JButton clearButton;
    private JButton myReservationButton;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private JPopupMenu booking_menu;
    private ICarService carService;
    Object[] col_booking_list;
    private  User user;

    public HomeView(User user){

        this.add(container);
        this.user = user;
        this.carService = new CarManager();
        FrameHelper.setupFrame(this, 1000, 495, "Rent A Car");
        this.bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = bookTable.rowAtPoint(e.getPoint());
                bookTable.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    booking_menu.show(bookTable, e.getX(), e.getY());
                }
            }
        });
        CreateTable(null);
        LoadFilter();
        CreateBooking();
        SearchCar();
        ClearSearch();

        myReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserBookView userBookView = new UserBookView(user);
            }
        });
    }


    public void CreateTable(ArrayList<Object[]> carList) {
        this.col_booking_list =new Object[] {"ID", "Brand", "Model", "Plate", "Color", "KM", "Year", "Type", "Fuel", "Gear"};

        if (carList == null || carList.isEmpty()) {

            if (carList == null || carList.isEmpty()) {
                System.out.println("The table is empty. No data available.");
                carList = new ArrayList<>();
                carList.add(new Object[]{"No data", "", "", "", "", "", ""});
            }
        }

        createTable(defaultTableModel, bookTable, col_booking_list, carList);
    }

    public void CreateBooking(){
        this.booking_menu = new JPopupMenu();
        this.booking_menu.add("Create Reservation").addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(bookTable.getValueAt(selectedRow, 0).toString());
                Car seletedCar = this.carService.getById(selectedId);
                UserCreateReservationView userCreateReservationView = new UserCreateReservationView( user , seletedCar);
                userCreateReservationView.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        CreateTable(null);

                    }
                });
            }


        });
        this.booking_menu.setComponentPopupMenu(booking_menu);

    }
    public void LoadFilter(){

        this.cmb_type.setModel(new DefaultComboBoxModel<>(entity.enums.Type.values()));
        this.cmb_type.setSelectedItem(null);
        this.cmb_fuel.setModel(new DefaultComboBoxModel<>(Fuel.values()));
        this.cmb_fuel.setSelectedItem(null);
        this.cmb_gear.setModel(new DefaultComboBoxModel<>(Gear.values()));
        this.cmb_gear.setSelectedItem(null);

    }
    public void SearchCar(){
        searchCarButton.addActionListener(e -> {
            ArrayList<Car> carArrayList = this.carService.searchForBooking(
                    txt_startDate.getText(),
                    txt_finishDate.getText(),
                    (entity.enums.Type) cmb_type.getSelectedItem(),
                    (Fuel) cmb_fuel.getSelectedItem(),
                    (Gear) cmb_gear.getSelectedItem()

            );
            ArrayList<Object[]> carBookingRow = this.carService.getForTable(col_booking_list.length,carArrayList);
            CreateTable(carBookingRow);

        });
    }
    public void ClearSearch(){

        clearButton.addActionListener(e -> {
            LoadFilter();

        });
    }


    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        this.txt_startDate = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.txt_finishDate = new JFormattedTextField(new MaskFormatter("##/##/####"));

        // Bugünün tarihini al ve formatla
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        // Alanlara bugünün tarihini ata
        this.txt_startDate.setText(formattedDate);
        this.txt_finishDate.setText(formattedDate);
    }
}
