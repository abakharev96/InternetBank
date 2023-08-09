package RestAPI.Bank.service.interfaces;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.entity.Operation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OperationService {
    void createOperation(Client client, String operationType, Double amount, Date date);
    List<Operation> readAllOperations();
    List<Operation> readByUserId(int userId);
    List<Operation> readByUserIdAndDateAfter(int userId, java.sql.Date date);
    List<Operation> readByUserIdAndDateBefore(int userId, java.sql.Date date);
    List<Operation> readbyUserIdAndDateAfterAndDateBefore(int userId, java.sql.Date dateStart, java.sql.Date dateEnd);
}
