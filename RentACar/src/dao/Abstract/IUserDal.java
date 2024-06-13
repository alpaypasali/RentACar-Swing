package dao.Abstract;

import entity.User;

import java.util.ArrayList;

public interface IUserDal {

    User signIn(String email , String password);
    boolean create(User user);
    User getById(int id);
    ArrayList<User> getAll();

}
