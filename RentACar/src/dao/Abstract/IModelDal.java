package dao.Abstract;

import entity.Model;

import java.util.ArrayList;

public interface IModelDal {
    ArrayList<Model> getAll();
    boolean create(Model model);
    boolean update(Model model);
    boolean delete(int id);
    Model getByid(int id);
    ArrayList<Model> selectByQuery(String query);


}
