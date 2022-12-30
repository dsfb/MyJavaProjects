package carsharing.data.customer;

import carsharing.data.ConnectionFactory;
import carsharing.sql.CustomerQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoJDBC implements CustomerDAO {
    private ConnectionFactory factory;

    public CustomerDaoJDBC(String fileName) {
        this.factory = new ConnectionFactory(fileName);
    }

    @Override
    public void createCustomerTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CustomerQuery.CREATE_TABLE.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        } finally {
            this.addForeignKeyCustomerTable();
            this.restartIdCustomerTable();
            this.addDefaultRentedCarCustomerTable();
        }
    }

    private void addForeignKeyCustomerTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CustomerQuery.ADD_FOREIGN_KEY.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    private void addDefaultRentedCarCustomerTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CustomerQuery.ADD_DEFAULT_RENTED_CAR.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    private void restartIdCustomerTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CustomerQuery.RESTART_WITH_ONE.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public void createCustomer(String name) {
        String sql = CustomerQuery.INSERT_INTO.getQuery();
        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public void rentACar(String name, int carId) {
        String sql = CustomerQuery.RENT_A_CAR.getQuery();
        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setInt(1, carId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public void returnACar(String name) {
        String sql = CustomerQuery.RENT_A_CAR.getQuery();
        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setObject(1, null);
            stmt.setString(2, name);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public int getRentedCar(String name) {
        String sql = CustomerQuery.GET_RENTED_CAR.getQuery();
        sql = String.format(sql, name);
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        throw new RuntimeException();
    }

    @Override
    public String getRentedCarName(int carId) {
        String sql = CustomerQuery.GET_RENTED_CAR_NAME.getQuery();
        sql = String.format(sql, carId);
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        throw new RuntimeException();
    }

    @Override
    public int getCompanyRentedCar(int carId) {
        String sql = CustomerQuery.GET_COMPANY_OF_RENTED_CAR.getQuery();
        sql = String.format(sql, carId);
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        throw new RuntimeException();
    }

    @Override
    public String getRentedCarCompanyName(int companyId) {
        String sql = CustomerQuery.GET_COMPANY_NAME_OF_RENTED_CAR.getQuery();
        sql = String.format(sql, companyId);
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        throw new RuntimeException();
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = CustomerQuery.GET_CUSTOMERS.getQuery();

        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                Customer c = new Customer(rs.getInt(1),
                        rs.getString(2));
                c.setRentedCarId(rs.getInt(3));
                customers.add(c);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        return customers;
    }
}
