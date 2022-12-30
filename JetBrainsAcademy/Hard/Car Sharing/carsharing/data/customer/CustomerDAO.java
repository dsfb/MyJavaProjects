package carsharing.data.customer;

import java.util.List;

public interface CustomerDAO {
    void createCustomerTable();
    void createCustomer(String name);
    void rentACar(String name, int carId);
    void returnACar(String name);
    int getRentedCar(String name);
    String getRentedCarName(int carId);
    int getCompanyRentedCar(int carId);
    String getRentedCarCompanyName(int companyId);
    List<Customer> getCustomers();
}
