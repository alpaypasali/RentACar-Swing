package business.Concrete;

import business.Exceptions.BusinessException;
import business.Exceptions.DatabaseException;
import business.Handlers.ErrorHandler;
import business.Handlers.SuccessInformationMessage;
import business.Services.IBookService;
import dao.Abstract.IBookDal;
import dao.Concrete.BookDal;
import entity.Book;
import entity.Model;

import java.util.ArrayList;

public class BookManager implements IBookService {

    private IBookDal bookDal;
    public BookManager(){
        this.bookDal = new BookDal();
    }
    @Override
    public SuccessInformationMessage create(Book book) {
        try {

            BookCannotBeEmpty(book);
            boolean createdModel = bookDal.create(book);
            if(createdModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Created Book successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage update(Book book) {
        try {

            BookCannotBeEmpty(book);
            boolean updatedModel = bookDal.update(book);
            if(updatedModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Updated Book successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage delete(int id) {
        try {

            BookCannotBeEmpty(id);
            boolean deletedModel = bookDal.delete(id);
            if(deletedModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Deleted Book successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Book getById(int id) {
        try{
            Book book = bookDal.getByid(id);
            this.BookCannotBeEmpty(book);
            return  book;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = bookDal.getAll();
        if(books.isEmpty())
            return  null;

        return books;
    }

    @Override
    public ArrayList<Book> getAll(int id) {
        ArrayList<Book> books = bookDal.getAll(id);
        if(books.isEmpty())
            return  null;

        return books;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<Book> modelList) {
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        if (modelList == null) {
            return modelObjList; // Return empty list if modelList is null
        }


        for (Book obj : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getCar().getPlate();
            rowObject[i++] = obj.getCar().getModel().getBrand().getName();
            rowObject[i++] = obj.getCar().getModel().getName();
            rowObject[i++] = obj.getUser().getName();
            rowObject[i++] = obj.getUser().getPhone();
            rowObject[i++] = obj.getUser().getEmail();
            rowObject[i++] = obj.getStart_date();
            rowObject[i++] = obj.getFinish_date();
            rowObject[i++] = obj.getPrice();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }
    @Override
    public ArrayList<Object[]> getForTableUser(int size, ArrayList<Book> modelList) {
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        if (modelList == null) {
            return modelObjList; // Return empty list if modelList is null
        }


        for (Book obj : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getCar().getModel().getBrand().getName();
            rowObject[i++] = obj.getCar().getModel().getName();
            rowObject[i++] = obj.getCar().getPlate();
            rowObject[i++] = obj.getStart_date();
            rowObject[i++] = obj.getFinish_date();
            rowObject[i++] = obj.getPrice();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }

    @Override
    public ArrayList<Book> searchForTable(int carId) {
        String query = "SELECT * FROM public.book";
        ArrayList<String> whereList = new ArrayList<>();

        if (carId != 0) {
            whereList.add("book_car_id = " + carId);
        }

        String whereStr = String.join(" AND ", whereList);
        if (!whereStr.isEmpty()) {
            query += " WHERE " + whereStr;
        }

        return this.bookDal.selectByQuery(query);
    }
    private  void BookCannotBeEmpty(Book book) throws BusinessException {

        if (book == null) {
            throw new BusinessException("Book cannot be null or empty");
        }


    }
    private  void BookCannotBeEmpty(int id) throws BusinessException {
        Book book = bookDal.getByid(id);
        if (book == null) {
            throw new BusinessException("Book cannot be null or empty");
        }


    }
}
