package carsharing.data.company;

import java.util.List;

public interface CompanyDAO {
    void createCompanyTable();
    void createCompany(String name);
    List<Company> getCompanies();
}
