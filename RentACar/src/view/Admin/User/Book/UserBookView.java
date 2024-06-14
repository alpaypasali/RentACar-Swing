package view.Admin.User.Book;

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

public class UserBookView extends AdminLayout {
    private  User user;
    private JPanel container;
    private JTable tbl_MyReservation;
    private DefaultTableModel defaultTableModel = new DefaultTableModel();
    private IBookService bookService;
    private JPopupMenu bookMenu;


    public UserBookView(User user){
        this.add(container);
        this.user = user;
        this.bookMenu = new JPopupMenu();
        this.bookService = new BookManager();
        FrameHelper.setupFrame(this, 1000, 495, "Rent A Car");
        this.tbl_MyReservation.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_MyReservation.rowAtPoint(e.getPoint());
                tbl_MyReservation.setRowSelectionInterval(selectedRow, selectedRow);
                if (SwingUtilities.isRightMouseButton(e)) {
                    bookMenu.show(tbl_MyReservation, e.getX(), e.getY());
                }
            }
        });
        CreateTable();
        DeleteBook();
    }

    public void CreateTable(){
        Object[] columnNames = {"ID", "Brand", "Model", "Plate", "StartDate", "FinishDate", "Price"};


        ArrayList<Object[]> booklist = bookService.getForTableUser(columnNames.length, this.bookService.getAll(user.getId()));


        if (booklist == null || booklist.isEmpty()) {
            System.out.println("The table is empty. No data available.");

            booklist = new ArrayList<>();
            booklist.add(new Object[]{"No data"});
        }

        createTable(defaultTableModel, tbl_MyReservation, columnNames, booklist);
    }
    public void DeleteBook(){

        this.bookMenu.add("Delete").addActionListener(e -> {
            int selectedRow = tbl_MyReservation.getSelectedRow();
            if (selectedRow != -1) {
                int selectedId = Integer.parseInt(tbl_MyReservation.getValueAt(selectedRow, 0).toString());

                SuccessInformationMessage brandUpdated =  this.bookService.delete(selectedId);
                brandUpdated.showMessageDialog();
                CreateTable();


            }
        });


    }
}
