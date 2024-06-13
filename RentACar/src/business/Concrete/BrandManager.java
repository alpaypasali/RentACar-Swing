package business.Concrete;

import business.Exceptions.BusinessException;
import business.Exceptions.DatabaseException;
import business.Handlers.ErrorHandler;
import business.Handlers.SuccessInformationMessage;
import business.Services.IBrandService;
import dao.Abstract.IBrandDal;
import dao.Concrete.BrandDal;
import entity.Brand;

import java.util.ArrayList;

public class BrandManager implements IBrandService {
    private IBrandDal brandDal;

    public BrandManager() {
        this.brandDal = new BrandDal();
    }
    @Override
    public ArrayList<Object[]> getForTable(int size) {
        ArrayList<Object[]> arrayList = new ArrayList<>();


        for (Brand u : this.getAll()) {
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = u.getId();
            rowObject[i++] = u.getName();
            arrayList.add(rowObject);
        }
        return arrayList;
    }

    @Override
    public SuccessInformationMessage create(Brand brand) {
        try {

            BrandCannotBeEmpty(brand);
            boolean createdModel = brandDal.create(brand);
            if(createdModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Created brand successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage update(Brand brand) {
        try {

            BrandCannotBeEmpty(brand);
            boolean updatedModel = brandDal.update(brand);
            if(updatedModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Updated brand successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public SuccessInformationMessage delete(int id) {
        try {

            BrandCannotBeEmpty(id);
            boolean deletedModel = brandDal.delete(id);
            if(deletedModel == false){

                throw new DatabaseException("Something Wrong database");
            }

            SuccessInformationMessage successMessage = new SuccessInformationMessage("Deleted brand successfully.");

            return successMessage;

        } catch (Exception e) {
            ErrorHandler.handleException(e);
            return null;

        }
    }


    @Override
    public Brand getById(int id) {
        try{
            Brand brand = brandDal.getByid(id);
            this.BrandCannotBeEmpty(brand);
            return  brand;

        }catch (Exception e){
            ErrorHandler.handleException(e);
            return null;

        }
    }

    @Override
    public ArrayList<Brand> getAll() {
        ArrayList<Brand> brands = brandDal.getAll();
        if(brands.isEmpty())
            return  null;

        return brands;
    }

    private  void BrandCannotBeEmpty(Brand brand) throws BusinessException {

        if (brand == null) {
            throw new BusinessException("Brand cannot be null or empty");
        }


    }
    private  void BrandCannotBeEmpty(int id) throws BusinessException {
        Brand brand = brandDal.getByid(id);
        if (brand == null) {
            throw new BusinessException("Brand cannot be null or empty");
        }


    }
}

