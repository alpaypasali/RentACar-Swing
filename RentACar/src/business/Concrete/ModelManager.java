package business.Concrete;

import business.Exceptions.BusinessException;
import business.Exceptions.DatabaseException;
import business.Handlers.ErrorHandler;
import business.Handlers.SuccessInformationMessage;
import business.Services.IModelService;
import dao.Abstract.IModelDal;
import dao.Concrete.ModelDal;
import entity.Model;

import java.util.ArrayList;

public class ModelManager implements IModelService {
    private final IModelDal modelDal;

    public ModelManager() {
        this.modelDal = new ModelDal();
    }
    @Override
    public ArrayList<Object[]> getForTable(int size, ArrayList<Model> modelList){
        ArrayList<Object[]> modelObjList = new ArrayList<>();
        if (modelList == null) {
            return modelObjList; // Return empty list if modelList is null
        }
        for (Model obj : modelList) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getBrand().getName();
            rowObject[i++] = obj.getName();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getYear();
            rowObject[i++] = obj.getFuel();
            rowObject[i++] = obj.getGear();
            modelObjList.add(rowObject);
        }
        return modelObjList;
    }
    @Override
    public SuccessInformationMessage create(Model model) {
        try {

            ModelCannotBeEmpty(model);
            boolean createdModel = modelDal.create(model);
            if(createdModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Created Model successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage update(Model model) {
        try {

            ModelCannotBeEmpty(model);
            boolean updatedModel = modelDal.update(model);
            if(updatedModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Updated Model successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage delete(int id) {
        try {

            ModelCannotBeEmpty(id);
            boolean deletedModel = modelDal.delete(id);
            if(deletedModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Deleted Model successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public Model getById(int id) {
        try{
            Model model = modelDal.getByid(id);
            this.ModelCannotBeEmpty(model);
            return  model;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Model> getAll() {
        ArrayList<Model> models = modelDal.getAll();
        if(models.isEmpty())
            return  null;

        return models;
    }

    private  void ModelCannotBeEmpty(Model model) throws BusinessException {

        if (model == null) {
            throw new BusinessException("Username cannot be null or empty");
        }


    }
    private  void ModelCannotBeEmpty(int id) throws BusinessException {
        Model model = modelDal.getByid(id);
        if (model == null) {
            throw new BusinessException("Username cannot be null or empty");
        }


    }
}
