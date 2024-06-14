package business.Services;

import business.Handlers.SuccessInformationMessage;
import entity.Book;


import java.util.ArrayList;

public interface IBookService {

    SuccessInformationMessage create(Book book);
    SuccessInformationMessage update(Book book);
    SuccessInformationMessage delete(int id);
    Book getById(int id);
    ArrayList<Book> getAll();
    ArrayList<Book> getAll(int id);
    ArrayList<Object[]> getForTable(int size, ArrayList<Book> modelList);
    ArrayList<Object[]> getForTableUser(int size, ArrayList<Book> modelList);
    ArrayList<Book> searchForTable(int carId);
}
