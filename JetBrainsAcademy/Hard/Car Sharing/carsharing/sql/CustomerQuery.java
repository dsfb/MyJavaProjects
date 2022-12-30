package carsharing.sql;

public enum CustomerQuery {
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS CUSTOMER " +
            "(id INTEGER NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(255) UNIQUE NOT NULL, " +
            "RENTED_CAR_ID INTEGER, " +
            "PRIMARY KEY ( id ))"),
    DROP_TABLE("DROP TABLE IF EXISTS CUSTOMER"),
    ADD_FOREIGN_KEY("ALTER TABLE CUSTOMER " +
            "ADD FOREIGN KEY (RENTED_CAR_ID) " +
            "REFERENCES CAR(ID)"),
    ADD_DEFAULT_RENTED_CAR("ALTER TABLE CUSTOMER ALTER COLUMN RENTED_CAR_ID SET DEFAULT NULL"),
    RESTART_WITH_ONE("ALTER TABLE CUSTOMER ALTER COLUMN id RESTART WITH 1"),
    INSERT_INTO("INSERT INTO CUSTOMER (name) VALUES(?)"),
    RENT_A_CAR("UPDATE CUSTOMER SET rented_car_id = ? WHERE name = ?"),
    GET_RENTED_CAR("SELECT rented_car_id FROM CUSTOMER WHERE name = '%s'"),
    GET_RENTED_CAR_NAME("SELECT name FROM CAR WHERE id = '%d'"),
    GET_COMPANY_OF_RENTED_CAR("SELECT company_id FROM CAR WHERE id = '%d'"),
    GET_COMPANY_NAME_OF_RENTED_CAR("SELECT name FROM COMPANY WHERE id = '%d'"),
    GET_CUSTOMERS("SELECT id, name, rented_car_id FROM CUSTOMER ORDER BY id ASC");

    private String query;

    CustomerQuery(String sqlQuery) {
        this.query = sqlQuery;
    }

    public String getQuery() {
        return query;
    }
}
