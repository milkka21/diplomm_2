package praktikum;

public class UserCreate {
    private String name;
    private String password;
    private String email;

    public UserCreate(String name, String password, String email) {
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
