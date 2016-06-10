package models;

/**
 *
 * @author Matheus
 */
public class User {
    int id;
    String cpf;
    String name;
    String email;
    String password;

    boolean manager;
    
    public static User loggedUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public void setAsLoggedUser() {
        User.loggedUser = this;
    }
    
    @Override
    public String toString() {
        return "ID: " + this.id + "\n" +
               "CPF: " + this.cpf + "\n" + 
               "Name: " + this.name + "\n" + 
               "Email: " + this.email + "\n" + 
               "Manager: " + this.manager + "\n";
    }

}
