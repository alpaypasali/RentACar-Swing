package dao.Abstract;

import entity.Brand;

import java.util.ArrayList;

public interface IBrandDal {
    ArrayList<Brand> getAll();
    boolean create(Brand brand);
    boolean update(Brand brand);
    boolean delete(int id);
    Brand getByid(int id);
}
