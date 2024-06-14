package business.Concrete;

import business.Exceptions.BusinessException;
import business.Exceptions.DatabaseException;
import business.Handlers.ErrorHandler;
import business.Handlers.SuccessInformationMessage;
import business.Services.ICarService;
import com.sun.tools.jconsole.JConsoleContext;
import dao.Abstract.IBookDal;
import dao.Abstract.ICarDal;
import dao.Concrete.BookDal;
import dao.Concrete.CarDal;
import entity.Book;
import entity.Car;
import entity.Model;
import entity.enums.Fuel;
import entity.enums.Gear;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class CarManager implements ICarService{
    private ICarDal carDal;
    private IBookDal bookDal;

   public  CarManager(){
       this.carDal = new CarDal();
       this.bookDal = new BookDal();
   }

    @Override
    public SuccessInformationMessage create(Car car) {
        try {

            CarCannotBeEmpty(car);
            boolean createdModel = carDal.create(car);
            if(createdModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Created Car successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage update(Car car) {
        try {

            CarCannotBeEmpty(car);
            boolean updatedCar = carDal.update(car);
            if(updatedCar == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Updated Car successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage delete(int id) {
        try {

            CarCannotBeEmpty(id);
            boolean deletedCar = carDal.delete(id);
            if(deletedCar == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Deleted Car successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Car getById(int id) {
        try{
            Car car = carDal.getByid(id);
            this.CarCannotBeEmpty(car);
            return  car;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Car> getAll() {
        ArrayList<Car> cars = carDal.getAll();
        if(cars.isEmpty())
            return  null;

        return cars;
    }

    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<Car> carArrayList) {
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        if (carArrayList == null) {
            return modelObjList; // Return empty list if modelList is null
        }
        for (Car obj : carArrayList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getModel().getBrand().getName();
            rowObject[i++] = obj.getModel().getName();
            rowObject[i++] = obj.getPlate();
            rowObject[i++] = obj.getColor();
            rowObject[i++] = obj.getKm();
            rowObject[i++] = obj.getModel().getYear();
            rowObject[i++] = obj.getModel().getType();
            rowObject[i++] = obj.getModel().getFuel();
            rowObject[i++] = obj.getModel().getGear();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }
    @Override
    public ArrayList<Car> searchForBooking(String strt_date, String fnsh_date, entity.enums.Type type, Fuel fuel, Gear gear) {

        strt_date = LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        fnsh_date = LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();


        ArrayList<String> where = new ArrayList<>();
        if (type != null) where.add("m.type = '" + type + "'");
        if (fuel != null) where.add("m.fuel = '" + fuel + "'");
        if (gear != null) where.add("m.gear = '" + gear + "'");


        String query = "SELECT * FROM public.car AS c LEFT JOIN public.model AS m ON c.model_id = m.id";
        if (!where.isEmpty()) query += " WHERE " + String.join(" AND ", where);


        ArrayList<Car> searchedCarList = this.carDal.selectByQuery(query);


        String bookQuery = String.format(
                "SELECT * FROM public.book WHERE " +
                        "'%s' BETWEEN start_date AND finish_date OR " +
                        "'%s' BETWEEN start_date AND finish_date OR " +
                        "start_date BETWEEN '%s' AND '%s' OR " +
                        "finish_date BETWEEN '%s' AND '%s'",
                strt_date, fnsh_date, strt_date, fnsh_date, strt_date, fnsh_date
        );


        ArrayList<Book> bookList = this.bookDal.selectByQuery(bookQuery);
        ArrayList<Integer> busyCarId = new ArrayList<>();
        for (Book book : bookList) {
            busyCarId.add(book.getCar_id());
        }


        searchedCarList.removeIf(car -> busyCarId.contains(car.getId()));

        return searchedCarList;
    }


    private  void CarCannotBeEmpty(Car car) throws BusinessException {

        if (car == null) {
            throw new BusinessException("Car cannot be null or empty");
        }


    }
    private  void CarCannotBeEmpty(int id) throws BusinessException {
        Car car = carDal.getByid(id);
        if (car == null) {
            throw new BusinessException("Car cannot be null or empty");
        }


    }

}
