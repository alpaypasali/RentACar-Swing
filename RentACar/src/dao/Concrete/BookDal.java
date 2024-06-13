package dao.Concrete;

import core.Db;
import dao.Abstract.IBookDal;
import entity.Book;

import java.sql.Connection;
import java.util.ArrayList;

public class BookDal implements IBookDal {

    private  final Connection conn;

    private static final String SELECT_ALL_QUERY = "SELECT * FROM public.brand ORDER BY id ASC";
    private static final String INSERT_QUERY = "INSERT INTO public.brand (name) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE public.brand SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM public.brand WHERE id = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM public.brand WHERE id = ?";

    public BookDal() {
        this.conn = Db.getInstance();
    }
    @Override
    public ArrayList<Book> getAll() {
        return null;
    }

    @Override
    public boolean create(Book book) {
        return false;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Book getByid(int id) {
        return null;
    }

    @Override
    public ArrayList<Book> selectByQuery(String query) {
        return null;
    }
}
