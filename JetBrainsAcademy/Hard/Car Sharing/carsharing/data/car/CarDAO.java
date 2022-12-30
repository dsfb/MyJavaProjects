package carsharing.data.car;

import java.util.List;

public interface CarDAO {
    void createCarTable();
    void createCar(String name, int companyId);
    List<Car> getCars(int companyId);
}
