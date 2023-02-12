package DAO;

import Beans.Company;
import Exceptions.EmptyCompanyIDException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompaniesDAO {
    /**
     * Checks if a company given email and password are correct - used for login.
     * @param email The company email.
     * @param password The company password.
     * @return If login successful returns the company ID, else returns -1;
     * @throws SQLException SQL Error
     */
    long isCompanyExist(String email, String password) throws SQLException;
    /**
     * Adds a new company to the DB.
     * @param company The company to be added - no ID field required.
     * @return The auto-generated ID for the added company.
     * @throws SQLException SQL Error
     */
    long addCompany(Company company) throws SQLException;
    /**
     * Updates a given company information.
     * @param company The company to be updated.
     * @throws SQLException SQL Error
     * @throws EmptyCompanyIDException The ID field inside company isn't initialized.
     */
    void updateCompany(Company company) throws SQLException, EmptyCompanyIDException;
    /**
     * Deletes a given company from the DB.
     * @param companyID The ID of a company to be deleted.
     * @throws SQLException SQL Error
     */
    void deleteCompany(long companyID) throws SQLException;

    /**
     *
     * @return List of all companies from DB.
     * @throws SQLException SQL Error
     */
    ArrayList<Company> getAllCompanies() throws SQLException;
    /**
     *
     * @param companyID The ID of the company to be fetched.
     * @return The fetched company.
     * @throws SQLException SQL Error
     */
    Company getOneCompany(long companyID) throws SQLException;
}
