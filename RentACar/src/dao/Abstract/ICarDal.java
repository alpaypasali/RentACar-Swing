package dao.Abstract;


import entity.Car;

import java.util.ArrayList;

public interface ICarDal {
    ArrayList<Car> getAll();
    boolean create(Car car);
    boolean update(Car car);
    boolean delete(int id);
    Car getByid(int id);
}
