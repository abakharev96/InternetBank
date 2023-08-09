package RestAPI.Bank.service;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.entity.Operation;
import RestAPI.Bank.repository.ClientRepository;
import RestAPI.Bank.repository.OperationRepository;
import RestAPI.Bank.service.interfaces.OperationService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final ClientRepository clientRepository;

    public OperationServiceImpl(OperationRepository operationRepository, ClientRepository clientRepository) {
        this.operationRepository = operationRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void createOperation(Client client, String operationType, Double amount, Date date) {
        Operation operation = new Operation();
        operation.setClient(client);
        operation.setOperationType(operationType);
        operation.setAmount(amount);
        operation.setDate(date);
        operationRepository.save(operation);
    }

    @Override
    public List<Operation> readAllOperations() {
        List<Operation> operations = operationRepository.findAll();
        if (operations.isEmpty()) {
            operations.add(new Operation());
        }
        return operations;
    }

    @Override
    public List<Operation> readByUserId(@RequestParam int userId) {
        List<Operation> operations = operationRepository.findOperationsByClientId(String.valueOf(userId));
        if ((operations.isEmpty()) || (!clientRepository.existsById(Integer.valueOf(userId)))){
            operations.add(new Operation());
        }
        return operations;
    }

    @Override
    public List<Operation> readByUserIdAndDateAfter(int userId, java.sql.Date dateEntered) {
        if (!isCorrectDate(dateEntered)) {
            List<Operation> operations = null;
            operations.add(new Operation());
            return operations;
        }
        java.sql.Date date = java.sql.Date.valueOf(dateEntered.toLocalDate());
        date = java.sql.Date.valueOf(date.toLocalDate());
        List<Operation> operations = operationRepository.findOperationsByClientIdAndDateAfter(String.valueOf(userId), date);
        return operations;
    }

    @Override
    public List<Operation> readByUserIdAndDateBefore(int userId, java.sql.Date dateEntered) {
        if (!isCorrectDate(dateEntered)) {
            List<Operation> operations = new ArrayList<>();
            operations.add(new Operation());
            return operations;
        }
        java.sql.Date date = java.sql.Date.valueOf(dateEntered.toLocalDate());
        date = java.sql.Date.valueOf(date.toLocalDate().plusDays(1));
        List<Operation> operations = operationRepository.findOperationsByClientIdAndDateBefore(String.valueOf(userId), date);
        return operations;
    }

    @Override
    public List<Operation> readbyUserIdAndDateAfterAndDateBefore(int userId, java.sql.Date dateStart, java.sql.Date dateEnd) {
        if ((!isCorrectDate(dateStart)) & (!isCorrectDate(dateEnd))) {
            List<Operation> operations = new ArrayList<>();
            operations.add(new Operation());
            return operations;
        }
        java.sql.Date dateStarted = java.sql.Date.valueOf(dateStart.toLocalDate());
        java.sql.Date dateEnded = java.sql.Date.valueOf(dateEnd.toLocalDate().plusDays(1));
        List<Operation> operations = operationRepository.findOperationsByClientIdAndDateAfterAndDateBefore(String.valueOf(userId), dateStarted, dateEnded);
        return operations;
    }

    public boolean isCorrectDate(java.sql.Date date) {
        try {
            date.toLocalDate();
        } catch (Exception e) {
            e.getMessage();
            return false;
        }
        return true;
    }

}
