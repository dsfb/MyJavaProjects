package carsharing.data.company;

import carsharing.data.ConnectionFactory;
import carsharing.sql.CompanyQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoJDBC implements CompanyDAO {
    private ConnectionFactory factory;

    public CompanyDaoJDBC(String fileName) {
        this.factory = new ConnectionFactory(fileName);
    }

    private void restartIdCompanyTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CompanyQuery.RESTART_WITH_ONE.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public void createCompanyTable() {
        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
        ) {
            String sql = CompanyQuery.CREATE_TABLE.getQuery();
            stmt.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        } finally {
            this.restartIdCompanyTable();
        }
    }

    @Override
    public void createCompany(String name) {
        String sql = CompanyQuery.INSERT_INTO.getQuery();
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
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        String sql = CompanyQuery.GET_COMPANIES.getQuery();

        try (
                Connection connection = this.factory.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                Company c = new Company(rs.getInt(1),
                        rs.getString(2));
                companies.add(c);
            }
        } catch (SQLException se) {
            se.printStackTrace();
            throw new RuntimeException(se);
        }

        return companies;
    }
}
