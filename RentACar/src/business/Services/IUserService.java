package business.Services;

import business.Handlers.SuccessInformationMessage;
import business.Handlers.SuccessMessage;
import entity.User;

import java.util.ArrayList;

public interface IUserService {
    SuccessMessage<User> signIn(String email, String password);
    SuccessInformationMessage create(User user);
    User getById(int id);
    ArrayList<User> getAll();
    ArrayList<Object[]> getForTable(int size);

}
