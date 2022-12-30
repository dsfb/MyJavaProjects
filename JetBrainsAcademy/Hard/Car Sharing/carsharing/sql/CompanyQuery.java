package carsharing.sql;

public enum CompanyQuery {
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS COMPANY " +
            "(id INTEGER NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(255) UNIQUE NOT NULL, " +
            "PRIMARY KEY ( id ))"),
    DROP_TABLE("DROP TABLE IF EXISTS COMPANY"),
    RESTART_WITH_ONE("ALTER TABLE COMPANY ALTER COLUMN id RESTART WITH 1"),
    INSERT_INTO("INSERT INTO company (name) VALUES(?)"),
    GET_COMPANIES("SELECT id, name FROM COMPANY ORDER BY id ASC");

    private String query;

    CompanyQuery(String sqlQuery) {
        this.query = sqlQuery;
    }

    public String getQuery() {
        return query;
    }
}
