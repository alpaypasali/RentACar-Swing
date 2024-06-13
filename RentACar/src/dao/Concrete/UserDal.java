package dao.Concrete;

import core.Db;
import dao.Abstract.IUserDal;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDal implements IUserDal {
    private  final Connection conn;

    public UserDal() {
        this.conn = Db.getInstance();
    }
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        try{
            ResultSet rs = this.conn.createStatement().executeQuery("SELECT * FROM public.user order by id ASC");
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User signIn(String email, String password) {
        User user = null;
        String query = "Select * from public.user where email = ? And password = ?";

        try{
            PreparedStatement pr = this.conn.prepareStatement(query);
            pr.setString(1,email);
            pr.setString(2,password);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                user = this.extractUser(rs);

            }
        }
        catch (SQLException e){

            e.printStackTrace();
        }
        return  user;
    }
    public boolean create(User user) {
        String query = "INSERT INTO public.user ( email, password, role, name , phone) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pr = this.conn.prepareStatement(query);
            pr.setString(1, user.getEmail());
            pr.setString(2, user.getPassword());
            pr.setString(3, user.getRole());
            pr.setString(4, user.getName());
            pr.setString(5, user.getPhone());

            return pr.executeUpdate() != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getById(int id) {
        User user = null;
        String query = "Select * from public.user where id = ?";
        try{
            PreparedStatement pr = conn.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next())
                user =this.extractUser(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  user;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setName(rs.getString("name"));
        user.setPhone(rs.getString("phone"));
        return user;
    }
}

