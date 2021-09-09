package DAO;

import Beans.Company;
import Beans.Coupon;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {
    void addCompany(Company company) throws SQLException;
    void updateCompany(Company company) throws SQLException;
    void deleteCompany(int companyID);
    ArrayList<Company> getAllCompanies();
    Company getOneCompany(int companyID);
    int getCompanyId(String email,String password);
    ArrayList<Coupon> getAllPurchasedByCouponID(int couponID);
}
