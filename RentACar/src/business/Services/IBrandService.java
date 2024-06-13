package business.Services;

import business.Handlers.SuccessInformationMessage;
import entity.Brand;

import java.util.ArrayList;

public interface IBrandService {
    SuccessInformationMessage create(Brand brand);
    SuccessInformationMessage update(Brand brand);
    SuccessInformationMessage delete(int id);
    Brand getById(int id);
    ArrayList<Brand> getAll();
    ArrayList<Object[]> getForTable(int size);
}
