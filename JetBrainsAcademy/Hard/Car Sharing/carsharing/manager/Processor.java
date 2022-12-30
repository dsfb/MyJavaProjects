package carsharing.manager;

import carsharing.data.car.Car;
import carsharing.data.car.CarDAO;
import carsharing.data.company.Company;
import carsharing.data.company.CompanyDAO;
import carsharing.data.customer.Customer;
import carsharing.data.customer.CustomerDAO;

import java.util.List;

public class Processor {
    private CompanyDAO companyDao;
    private CarDAO carDao;
    private CustomerDAO customerDAO;

    private KeyboardReader reader;

    private boolean loggedAsManager = false;
    private boolean loggedAsCustomer = false;

    private int companyId = 0;
    private String companyName = "";

    private int customerId = 0;
    private String customerName = "";

    public Processor(CompanyDAO companyDao, CarDAO carDao, CustomerDAO customerDAO) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDAO = customerDAO;
        this.reader = new KeyboardReader();
    }

    private int getFirstOption() {
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit\n");
        return this.reader.readInteger();
    }

    private int getLoggedOption() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");
        return this.reader.readInteger();
    }

    private int getCompanyOption() {
        System.out.printf("%s company:\n", this.companyName);
        System.out.println("1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");
        return this.reader.readInteger();
    }

    private boolean listCompanies() {
        List<Company> companies = this.companyDao.getCompanies();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
            return false;
        } else {
            System.out.println();
            System.out.println("Choose a company:");
            for (Company c : companies) {
                System.out.printf("%d. %s\n", c.getId(), c.getName());
            }
            System.out.println("0. Back");

            int result = this.reader.readInteger();
            if (result > 0) {
                this.companyName = companies.get(result - 1).getName();
                this.companyId = result;
                return true;
            } else {
                return false;
            }
        }
    }

    private void createCompany() {
        System.out.println();
        System.out.println("Enter the company name:");
        String name = this.reader.readLine();
        this.companyDao.createCompany(name);
        System.out.println("The company was created!");
    }

    private void listCompanyCars() {
        System.out.println();
        List<Car> cars = this.carDao.getCars(this.companyId);
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.printf("%s cars:\n", this.companyName);
            for (int i = 0; i < cars.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, cars.get(i).getName());
            }
        }
    }

    private void createCar() {
        System.out.println();
        System.out.println("Enter the car name:");
        String carName =  this.reader.readLine();
        this.carDao.createCar(carName, this.companyId);
        System.out.println("The car was added!");
    }

    private void initTables() {
        this.companyDao.createCompanyTable();
        this.carDao.createCarTable();
        this.customerDAO.createCustomerTable();
    }

    public void init() {
        this.initTables();
    }

    public void close() {
        this.reader.close();
    }

    private int getCustomerFromList() {
        List<Customer> customers = this.customerDAO.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            return 0;
        }

        System.out.println("Customer list:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s%n", i + 1,
                    customers.get(i).getName());
        }
        System.out.println("0. Back");
        int result = this.reader.readInteger();
        if (result > 0) {
            this.customerId = result - 1;
            this.customerName = customers.get(result - 1).getName();
        }

        return result;
    }

    private int getCustomerOption() {
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");
        return this.reader.readInteger();
    }

    public void process() {
        int option;
        while (true) {
            if (!this.loggedAsManager && !this.loggedAsCustomer) {
                option = getFirstOption();
                if (option == 0) {
                    System.out.println();
                    return;
                } else if (option == 1) {
                    this.loggedAsManager = true;
                } else if (option == 2) {
                    this.loggedAsCustomer = true;
                } else if (option == 3) {
                    System.out.println("Enter the customer name:");
                    String name = this.reader.readLine();
                    this.customerDAO.createCustomer(name);
                    System.out.println("The customer was added!");
                }
            } else if (this.loggedAsCustomer) {
                option = this.getCustomerFromList();
                if (option == 0) {
                    this.loggedAsCustomer = false;
                } else {
                    do {
                        option = this.getCustomerOption();
                        if (option == 1) {
                            List<Company> companies = this.companyDao.getCompanies();
                            if (this.customerDAO.getRentedCar(this.customerName) > 0) {
                                System.out.println("You've already rented a car!");
                            } else if (companies.isEmpty()) {
                                System.out.println("The company list is empty!");
                            } else {
                                System.out.println("Choose a company:");
                                for (int i = 0; i < companies.size(); i++) {
                                    System.out.printf("%d. %s\n", i + 1, companies.get(i).getName());
                                }
                                System.out.println("0. Back");
                                int companyOption = this.reader.readInteger();
                                if (companyOption == 0) {
                                    continue;
                                }
                                List<Car> cars = this.carDao.
                                        getCars(companies.get(companyOption - 1).getId());
                                if (cars.isEmpty()) {
                                    System.out.printf("No available cars in the '%s' company\n",
                                            companies.get(companyOption - 1).getName());
                                } else {
                                    System.out.println("Choose a car:");
                                    for (int i = 0; i < cars.size(); i++) {
                                        System.out.printf("%d. %s\n", i + 1, cars.get(i).getName());
                                    }
                                    System.out.println("0. Back");
                                    int carOption = this.reader.readInteger();
                                    if (carOption == 0) {
                                        continue;
                                    }
                                    this.customerDAO.rentACar(this.customerName, cars.get(carOption - 1).getId());
                                    System.out.printf("You rented '%s'\n", cars.get(carOption - 1).getName());
                                }
                            }
                        } else if (option == 2) {
                            int carId = this.customerDAO.getRentedCar(
                                    this.customerName);
                            if (carId == 0) {
                                System.out.println("You didn't rent a car!");
                            } else {
                                this.customerDAO.returnACar(this.customerName);
                                System.out.println("You've returned a rented car!");
                            }
                        } else if (option == 3) {
                            int carId = this.customerDAO.getRentedCar(
                                    this.customerName);
                            if (carId == 0) {
                                System.out.println("You didn't rent a car!");
                            } else {
                                System.out.println("Your rented car:");
                                System.out.println(this.customerDAO.getRentedCarName(carId));
                                System.out.println("Company:");
                                int companyId = this.customerDAO.getCompanyRentedCar(carId);
                                System.out.println(this.customerDAO.getRentedCarCompanyName(companyId));
                            }
                        }
                    } while (option > 0);
                }
            } else if (this.loggedAsManager) {
                option = getLoggedOption();
                if (option == 0) {
                    this.loggedAsManager = false;
                } else if (option == 1) {
                    if (this.listCompanies()) {
                        do {
                            System.out.println();
                            option = this.getCompanyOption();
                            if (option == 1) {
                                this.listCompanyCars();
                            } else if (option == 2) {
                                this.createCar();
                            }
                        } while (option > 0);
                    }
                } else if (option == 2) {
                    this.createCompany();
                }
            }
            System.out.println();
        }
    }
}
