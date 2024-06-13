package business.Services;

import business.Handlers.SuccessInformationMessage;
import entity.Model;

import java.util.ArrayList;

public interface IModelService {
    SuccessInformationMessage create(Model model);
    SuccessInformationMessage update(Model model);
    SuccessInformationMessage delete(int id);
    Model getById(int id);
    ArrayList<Model> getAll();
    ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList);

}

