package dao.Concrete;

import core.Db;
import dao.Abstract.IBookDal;
import dao.Abstract.IBrandDal;
import dao.Abstract.ICarDal;
import dao.Abstract.IUserDal;
import entity.Book;
import entity.Model;
import entity.enums.Fuel;
import entity.enums.Gear;
import entity.enums.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class BookDal implements IBookDal {
    private IUserDal userDal;
    private ICarDal carDal;
    private  final Connection conn;
    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.book ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.book (car_id, user_id, price, start_date, finish_date, note) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE public.book SET car_id = ?, user_id = ?, price = ?, start_date = ?, finish_date = ?, note = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.book WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.book WHERE id = ?";



    public BookDal() {
        this.conn = Db.getInstance();
        this.userDal = new UserDal();
        this.carDal = new CarDal();
    }
    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();
        try (PreparedStatement pr = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pr.executeQuery()) {
            while (rs.next()) {
                books.add(extractUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public boolean create(Book book) {
        return executeUpdate(INSERT_QUERY, book);
    }

    @Override
    public boolean update(Book book) {
        return executeUpdate(UPDATE_QUERY, book);
    }

    @Override
    public boolean delete(int id) {
        try (PreparedStatement pr = conn.prepareStatement(DELETE_QUERY)) {
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Book getByid(int id) {
        Book book = null;
        try (PreparedStatement pr = conn.prepareStatement(SELECT_BY_ID_QUERY)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next())
                book = extractUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public ArrayList<Book> selectByQuery(String query) {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        try {
            ResultSet rs = this.conn.createStatement().executeQuery(query);
            while (rs.next()) {
                bookArrayList.add(this.extractUser(rs));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return bookArrayList;
    }

    @Override
    public ArrayList<Book> getAll(int userId) {
        ArrayList<Book> books = new ArrayList<>();
        String query = "SELECT * FROM public.book WHERE user_id = ?";
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, userId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                books.add(extractUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    private boolean executeUpdate(String query, Book book) {
        try (PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, book.getCar_id());
            pr.setInt(2, book.getUser_id());
            pr.setInt(3, book.getPrice());
            pr.setDate(4, java.sql.Date.valueOf(book.getStart_date()));
            pr.setDate(5, java.sql.Date.valueOf(book.getFinish_date()));
            pr.setString(6, book.getNote());

            if (query.equals(UPDATE_QUERY)) {
                pr.setInt(7, book.getId());
            }
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Book extractUser(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setCar_id(rs.getInt("car_id"));
        book.setUser_id(rs.getInt("user_id"));
        book.setPrice(rs.getInt("price"));
        book.setNote(rs.getString("note"));
        book.setStart_date(LocalDate.parse(rs.getString("start_date")));
        book.setFinish_date(LocalDate.parse(rs.getString("finish_date")));
        book.setUser(this.userDal.getById(rs.getInt("user_id")));
        book.setCar(this.carDal.getByid(rs.getInt("car_id")));
        return book;
    }
}
