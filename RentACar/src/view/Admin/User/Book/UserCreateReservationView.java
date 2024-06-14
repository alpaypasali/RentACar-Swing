package view.Admin.User.Book;

import business.Concrete.BookManager;
import business.Handlers.SuccessInformationMessage;
import business.Helpers.FrameHelper;
import business.Services.IBookService;
import entity.Book;
import entity.Car;
import entity.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class UserCreateReservationView extends JFrame {
    private  User user;
    private  Car car;
    private IBookService bookService;
    private JPanel container;
    private JTextField txt_car;
    private JTextField txt_name;
    private JTextArea txt_note;
    private JTextField txt_phone;
    private JPanel txt_email;
    private JLabel startDate;
    private JTextField txt_mail;
    private JTextField finish_date;
    private JTextField txt_price;
    private JButton createReservationButton;
    private JFormattedTextField txt_start;
    private Book book;

    public UserCreateReservationView(User user , Car car ){
        this.add(container);
        this.user = user;
        this.car = car;
        this.book = new Book();
        this.bookService = new BookManager();
        FrameHelper.setupFrame(this, 300, 600, "Rent A Car");

        txt_car.setText(this.car.getPlate() + " " + this.car.getModel().getBrand().getName()+ " " + this.car.getModel().getName());
        txt_name.setText(this.user.getName());
        txt_mail.setText(this.user.getEmail());
        txt_phone.setText(this.user.getPhone());

        txt_start.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                calculatePrice();
            }
            public void removeUpdate(DocumentEvent e) {
                calculatePrice();
            }
            public void insertUpdate(DocumentEvent e) {
                calculatePrice();
            }
        });

        finish_date.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                calculatePrice();
            }
            public void removeUpdate(DocumentEvent e) {
                calculatePrice();
            }
            public void insertUpdate(DocumentEvent e) {
                calculatePrice();
            }
        });


        createReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book.setCar_id(car.getId());
                book.setUser_id(user.getId());
                book.setPrice(Integer.parseInt(txt_price.getText()));
                book.setStart_date(LocalDate.parse(txt_start.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setFinish_date(LocalDate.parse(finish_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                book.setNote(txt_note.getText());
                SuccessInformationMessage createdBook =bookService.create(book);
                createdBook.getMessage();
                dispose();

            }
        });
    }

    private void createUIComponents() throws ParseException {
        // TODO: place custom component creation code here
        this.txt_start = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.finish_date = new JFormattedTextField(new MaskFormatter("##/##/####"));

        // Bugünün tarihini al ve formatla
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);

        // Alanlara bugünün tarihini ata
        this.txt_start.setText(formattedDate);
        this.finish_date.setText(formattedDate);
    }
    private void calculatePrice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate startDate = LocalDate.parse(txt_start.getText(), formatter);
            LocalDate endDate = LocalDate.parse(finish_date.getText(), formatter);

            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            int totalPrice = (int) (daysBetween * car.getDaily_price());

            txt_price.setText(String.valueOf(totalPrice));
        } catch (Exception ex) {
            txt_price.setText("0");
        }
    }
}
