package Facade;

import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomerDAO;


/**
 * This class is an abstract class of all the facades in the system.
 * <p>
 * This class has 3 attributes of dao's to give us access to the methods in the DBDAO'S.
 */
public abstract class ClientFacade {
    protected CompaniesDAO companiesDAO;
    protected CustomerDAO customerDAO;
    protected CouponsDAO couponsDAO;
    /**
     * This is constructor with no arguments. gives us instance of all the DBDAO'S to have permission to use their methods..
     */
    public ClientFacade() {

    }
    /**
     * This is abstact method we will implements in the child classes .
     * @param email email of the user that try to log in
     * @param password the password of the user that try to log in
     */
    public  abstract boolean login(String email, String password);
}
