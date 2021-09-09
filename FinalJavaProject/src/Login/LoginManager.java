package Login;

import Facade.AdminFacade;
import Facade.ClientFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import SQL.ConnectionPool;

/**
 * This class is responsible of the management of the login process when you enter
 * email and pass it will navigate you to the specific facade u logged in.
 * <p>
 * This class has 1 attribute from Logic manager . because it is a singleton the instance should be null first.
 */
public class LoginManager {
    private static LoginManager instance = null;

    /**
     * private ctor because its singleton. no params
     */
    private LoginManager() {
    }

    /**
     * public ctor with double check for the instance of the connection pool.
     *
     * @return return instance of login manager
     */
    public static LoginManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    /**
     * This method getting email and password and client type(enum).
     *
     * @param email      the email of the specific login access
     * @param password   the password of the account who try to log in
     * @param clientType the enum type of the login
     * @return return the specific client facade.
     */
    public ClientFacade login(String email, String password, ClientType clientType) {
        switch (clientType) {
            case COMPANY:
                CompanyFacade cf = new CompanyFacade();
                return cf.login(email, password) ? cf : null;
            case ADMINISTRATOR:
                AdminFacade af = new AdminFacade();
                return af.login(email, password) ? af : null;
            case CUSTOMER:
                CustomerFacade cusf = new CustomerFacade();
                return cusf.login(email, password) ? cusf : null;
            default:
                return null;
        }
    }
}