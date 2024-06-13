package business.Concrete;

import business.Exceptions.BusinessException;
import business.Exceptions.DatabaseException;
import business.Handlers.ErrorHandler;
import business.Handlers.SuccessInformationMessage;
import business.Services.ICarService;
import com.sun.tools.jconsole.JConsoleContext;
import dao.Abstract.ICarDal;
import dao.Concrete.CarDal;
import entity.Car;
import entity.Model;

import java.util.ArrayList;

public class CarManager implements ICarService{
    private ICarDal carDal;

   public  CarManager(){
       this.carDal = new CarDal();
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
