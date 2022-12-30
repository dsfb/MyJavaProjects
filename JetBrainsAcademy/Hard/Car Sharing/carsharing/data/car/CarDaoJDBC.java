package carsharing.data.car;

import carsharing.data.ConnectionFactory;
import carsharing.sql.CarQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoJDBC implements CarDAO {
    private ConnectionFactory factory;

    public CarDaoJDBC(String fileName) {
        this.factory = new ConnectionFactory(fileName);
    }

    @Override
    public void createCarTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CarQuery.CREATE_TABLE.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        } finally {
            this.addForeignKeyCarTable();
            this.restartIdCarTable();
        }
    }

    private void addForeignKeyCarTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CarQuery.ADD_FOREIGN_KEY.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    private void restartIdCarTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CarQuery.RESTART_WITH_ONE.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public void createCar(String name, int companyId) {
        String sql = CarQuery.INSERT_INTO.getQuery();
        try (
                Connection connection = this.factory.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setString(1, name);
            stmt.setInt(2, companyId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public List<Car> getCars(int companyId) {
        List<Car> cars = new ArrayList<>();
        String sql = CarQuery.GET_CARS.getQuery();
        sql = String.format(sql, companyId);

        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                Car c = new Car(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3));
                cars.add(c);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        return cars;
    }
}
