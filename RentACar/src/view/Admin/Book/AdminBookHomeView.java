package view.Admin.Book;

import business.Concrete.BookManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.FrameHelper;
import business.Services.IBookService;
import entity.User;
import view.AdminLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminBookHomeView extends AdminLayout {
    private JPanel container;
    private JTable tbl_reservations;
    private IBookService bookService;
    private  JPopupMenu bookMenu;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private  User user;

    public  AdminBookHomeView(User user){
        this.add(container);
        this.bookMenu = new JPopupMenu();
        this.bookService = new BookManager();
        this.user = user;
        FrameHelper.setupFrame(this, 1000, 495, "Rent A Car");
        this.tbl_reservations.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_reservations.rowAtPoint(e.getPoint());
                tbl_reservations.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    bookMenu.show(tbl_reservations, e.getX(), e.getY());
                }
            }
        });

        CreateTable();
        DeleteBook();

    }

    public void CreateTable(){
        Object[] columnNames = {"ID", "Plate", "Brand", "Model", "Name", "Phone", "E-Mail" , "StartDate" ,"FinishDate" , "Price"};

        ArrayList<Object[]> booklist = bookService.getForTable(columnNames.length, this.bookService.getAll());


        if (booklist == null || booklist.isEmpty()) {
            System.out.println("The table is empty. No data available.");

            booklist = new ArrayList<>();
            booklist.add(new Object[]{"No data"});
        }

        createTable(defaultTableModel, tbl_reservations, columnNames, booklist);
    }
    public void DeleteBook(){

        this.bookMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_reservations.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_reservations.getValueAt(selectedRow, 0).toString());

                SuccessInformationMessage brandUpdated =  this.bookService.delete(selectedId);
                brandUpdated.showMessageDialog();
                CreateTable();


            }
        });


    }
}
