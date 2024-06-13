package business.Services;

import business.Handlers.SuccessInformationMessage;
import entity.Model;
import entity.enums.Fuel;
import entity.enums.Gear;
import entity.enums.Type;

import java.util.ArrayList;

public interface IModelService {
    SuccessInformationMessage create(Model model);
    SuccessInformationMessage update(Model model);
    SuccessInformationMessage delete(int id);
    Model getById(int id);
    ArrayList<Model> getAll();
    ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList);
    ArrayList<Model> searchForTable(int brandId , Fuel fuel , Gear gear, Type type);

}

