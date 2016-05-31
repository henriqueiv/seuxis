package Login;

import Model.User;
import Model.UserDAO;
import Order.OrderView;

/**
 *
 * @author valcanaia
 */
public class LoginController {

    public Boolean validateCpf(String cpf) {
        return true;
    }

    public Boolean loginWithUser(User user) {
        UserDAO dao = new UserDAO();
        User _user = dao.getUserByCpf(user.getCpf());
        Boolean logged = (_user != null);
        if (logged) {
            _user.setAsLoggedUser();
        }

        return logged;
    }

    void goToMainScreen() {
        OrderView orderView = new OrderView();
        orderView.setVisible(true);
    }

}
