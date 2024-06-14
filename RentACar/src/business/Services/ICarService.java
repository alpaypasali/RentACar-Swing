package business.Services;

import business.Handlers.SuccessInformationMessage;
import entity.Car;
import entity.enums.Fuel;
import entity.enums.Gear;


import java.util.ArrayList;

public interface ICarService {
    SuccessInformationMessage create(Car car);
    SuccessInformationMessage update(Car car);
    SuccessInformationMessage delete(int id);
    Car getById(int id);
    ArrayList<Car> getAll();
    ArrayList<Object[]> getForTable(int size, ArrayList<Car> carArrayList);
    ArrayList<Car> searchForBooking(String strt_date, String fnsh_date, entity.enums.Type type, Fuel fuel, Gear gear);
}
