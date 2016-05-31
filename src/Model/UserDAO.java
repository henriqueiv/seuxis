package Model;

/**
 *
 * @author valcanaia
 */
public class UserDAO extends BaseDAO<User> {

    @Override
    public void save(User t) {
        String sql = "INSERT INTO USERS(ID, CPF, MANAGER) VALUES (" + t.getId() + ", '" + t.getCpf() + "', " + t.isManager() + ")";
        System.out.println(sql);
    }

    @Override
    public void delete(User t) {
        String sql = "DELETE FROM USERS WHERE ID = " + t.getId() + ";";
        System.out.println(sql);
    }

    @Override
    public User getById(Integer id) {
        String sql = "SELECT * FROM USERS";
        if (id != null) {
            sql += "WHERE ID = " + id + ";";
        }
        System.out.println(sql);

        return null;
    }

    @Override
    public Integer getMaxId() {
        String sql = "SELECT MAX(ID)+1 FROM USERS";
        return 1;
    }

    public User getUserByCpf(String cpf) {
        String sql = "SELECT ID, CPF, MANAGER FROM USERS WHERE CPF = '" + cpf + "'";
        System.out.println(sql);
        
        User user = new User();
        user.setManager(true);
        return user;
    }

}
