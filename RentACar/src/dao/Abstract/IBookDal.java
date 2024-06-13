package dao.Abstract;

import entity.Book;
import entity.Model;

import java.util.ArrayList;

public interface IBookDal {
    ArrayList<Book> getAll();
    boolean create(Book book);
    boolean update(Book book);
    boolean delete(int id);
    Book getByid(int id);
    ArrayList<Book> selectByQuery(String query);
}
