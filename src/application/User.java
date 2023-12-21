package application;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String number;

    public User(int id, String username, String email, String password ,String number) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    
    public String getNumber() {
        return number;
    }
    

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
