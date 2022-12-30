package carsharing;

import carsharing.data.car.CarDAO;
import carsharing.data.car.CarDaoJDBC;
import carsharing.data.company.CompanyDAO;
import carsharing.data.company.CompanyDaoJDBC;
import carsharing.data.customer.CustomerDAO;
import carsharing.data.customer.CustomerDaoJDBC;
import carsharing.manager.Processor;

public class Main {
    /*
     Used links in this task, as suggested by JetBrains Academy:
     Stage 1: https://www.tutorialspoint.com/h2_database/h2_database_jdbc_connection.htm
     Stage 2: https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm
     */
    public static void main(String[] args) {
        String databaseFileName = "carsharing.mv.db";
        if (args[0].equals("-databaseFileName") &&
                args.length > 1) {
            databaseFileName = args[1];
        }

        CompanyDAO companyDao = new CompanyDaoJDBC(databaseFileName);
        CarDAO carDAO = new CarDaoJDBC(databaseFileName);
        CustomerDAO customerDAO = new CustomerDaoJDBC(databaseFileName);
        Processor proc = new Processor(companyDao, carDAO, customerDAO);
        proc.init();
        proc.process();
        proc.close();
    }
}