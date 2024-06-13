package entity;

public class User {
    private  int id;
    private  String password;
    private  String role = "member";
    private  String name;
    private  String phone;
    private  String email;

    public User() {
    }

    public User(int id, String email, String password,  String name, String phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = "member";
        this.name = name;
        this.phone = phone;

    }

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role =  "member";;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role =  "member";;

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
