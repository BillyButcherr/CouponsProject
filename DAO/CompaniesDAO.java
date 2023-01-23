package DAO;

import Beans.Company;
import Exceptions.EmptyCompanyIDException;

import java.util.ArrayList;

public interface CompaniesDAO {
    long isCompanyExist(String email, String password);
    long addCompany(Company company);
    void updateCompany(Company company) throws EmptyCompanyIDException;
    void deleteCompany(Company company);
    ArrayList<Company> getAllCompanies();
    Company getOneCompany(long companyID);
}
