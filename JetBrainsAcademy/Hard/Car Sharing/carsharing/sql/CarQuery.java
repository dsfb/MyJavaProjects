package carsharing.sql;

public enum CarQuery {
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS CAR " +
            "(id INTEGER NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(255) UNIQUE NOT NULL, " +
            "COMPANY_ID INTEGER NOT NULL, " +
            "PRIMARY KEY ( id ))"),
    DROP_TABLE("DROP TABLE IF EXISTS CAR"),
    ADD_FOREIGN_KEY("ALTER TABLE CAR " +
            "ADD FOREIGN KEY (COMPANY_ID) " +
            "REFERENCES COMPANY(ID)"),
    RESTART_WITH_ONE("ALTER TABLE CAR ALTER COLUMN id RESTART WITH 1"),
    INSERT_INTO("INSERT INTO CAR (name, company_id) VALUES(?, ?)"),
    GET_CARS("SELECT id, name, company_id FROM CAR WHERE company_id = '%d' ORDER BY id ASC");

    private String query;

    CarQuery(String sqlQuery) {
        this.query = sqlQuery;
    }

    public String getQuery() {
        return query;
    }
}
