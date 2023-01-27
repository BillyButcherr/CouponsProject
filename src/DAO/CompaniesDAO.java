package DAO;

import Beans.Company;
import Exceptions.EmptyCompanyIDException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {
    long isCompanyExist(String email, String password) throws SQLException;

    long addCompany(Company company) throws SQLException;

    void updateCompany(Company company) throws SQLException, EmptyCompanyIDException;

    void deleteCompany(Company company) throws SQLException;

    ArrayList<Company> getAllCompanies() throws SQLException;

    Company getOneCompany(long companyID) throws SQLException;
}
