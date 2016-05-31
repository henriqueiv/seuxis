package Model;

/**
 *
 * @author valcanaia
 */
public class User {
    int id;
    String cpf;
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

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public void setAsLoggedUser() {
        User.loggedUser = this;
    }

}
