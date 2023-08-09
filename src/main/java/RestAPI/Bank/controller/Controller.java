package RestAPI.Bank.controller;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.entity.Operation;
import RestAPI.Bank.repository.ClientRepository;
import RestAPI.Bank.service.interfaces.ClientService;
import RestAPI.Bank.service.interfaces.OperationService;
import RestAPI.Bank.service.interfaces.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class Controller {

    @Autowired
    private ClientService clientService;
    @Autowired
    private OperationService operationService;
    @Autowired
    private TransferService transferService;
    private final ClientRepository clientRepository;

    public Controller(ClientRepository clientRepository, OperationService operationService, TransferService transferService) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.operationService = operationService;
        this.transferService = transferService;
    }

    @GetMapping(value = "/")
    public String printWelcome(){
        String welcome = "Hello! This is short guide how to use the program. " + "\n" +
                "realised Method: " + "\n" +
                "1. getBalance; " + "\n" +
                "You can call it as /client={user_id}." + "\n" +
                "Example: /client=1 for the client with id equals 1." + "\n" +
                "2. putMoney; " + "\n" +
                "You can call it as /client={user_id}&addamount={amount}." + "\n" +
                "Example: /client=1&addamount=50.5 for the client with id equals 1 and that want to put 50.5 money" + "\n" +
                "3. takeMoney; " + "\n" +
                "You can call it as /client={user_id}&decramount={amount}. " + "\n" +
                "Example: /client=3&decramount=30 for the client with id equals 3 and that want to take 30 money." + "\n" +
                "4. getalloperations; " + "\n" +
                "Print all operations, you can call it as /getalloperations. " + "\n" +
                "5. getoperationsbyuser; " + "\n" +
                "You can set user that operations you want to present and restrict by dates. " + "\n" +
                "To configure this option, please use one of the following links: " + "\n" +
                "/getoperationbyclient={user_id} - to present operations by user; " + "\n" +
                "/getoperationbyclient={user_id}/fromdate={date} - to present operations by user started from mentioned date; " + "\n" +
                "/getoperationbyclient={user_id}/todate={date} - to present operations by user started up to mentioned date; " + "\n" +
                "/getoperationbyclient={user_id}/fromdate={dateStarted}/todate={dateEnded} - " + "\n" +
                "to present operations by user started from date and up to mentioned date. " + "\n" +
                "6. tranferMoney;" + "\n" +
                "You can call it as /transfermonetyfrom={user_sender_id}to={user_receiver_id}&amount={amount} " + "\n" +
                "Example: /transfermonetyfrom=1to=4&amount=25. " + "\n" +
                "This configuration will send 25 money from client 1 to client 4 and add transactions in operation list.";

        return welcome;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Part for clients
    //------------------------------------------------------------------------------------------------------------------

    @GetMapping(value = "/clients")
    public List<Client> readAll(){
        List<Client> clients = clientService.readAll();
        return clients;
    }

    @GetMapping(value = "/client={user_id}")
    public Client getBalance(@PathVariable(name = "user_id") String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.getMessage();
            return new Client();
        }
        final Client client = clientService.read(Integer.parseInt(id));
        return client;
    }

    @PutMapping(value = "/client={user_id}&addamount={amount}")
    public String updateAdd(@PathVariable(name = "user_id") String id, @PathVariable(name = "amount") String amount) {
        try{
            Double.parseDouble(amount);
            Integer.parseInt(id);
            if (!clientRepository.existsById(Integer.parseInt(id))){
                return "Entered client ID does not exist in the databse.";
            }
        } catch (NumberFormatException e) {
            e.getMessage();
            return "Check entered value please. Current value is not appropriate.";
        }
        String updated = clientService.putMoney(Double.parseDouble(amount), Integer.parseInt(id));
        operationService.createOperation(new Client(Integer.parseInt(id)), "putMoney", Double.parseDouble(amount), new Date());
        return updated;
    }

    @PutMapping(value = "/client={user_id}&decramount={amount}")
    public String updateDecr(@PathVariable(name = "user_id") String id, @PathVariable(name = "amount") String amount) {
        try{
            Double.parseDouble(amount);
            Integer.parseInt(id);
            if (!clientRepository.existsById(Integer.parseInt(id))){
                return "Entered client ID does not exist in the databse.";
            }
        } catch (NumberFormatException e) {
            e.getMessage();
            return "Check entered value please. Current value is not appropriate.";
        }
        String updated = clientService.takeMoney(Double.parseDouble(amount), Integer.parseInt(id));
        operationService.createOperation(new Client(Integer.parseInt(id)), "takeMoney", Double.parseDouble(amount), new Date());
        return updated;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Part for Operations
    //------------------------------------------------------------------------------------------------------------------

    @GetMapping(value = "/getalloperations")
    public List<Operation> readAllOperations() {
        return operationService.readAllOperations();
    }

    @GetMapping(value = "/getoperationbyclient={user_id}")
    public List<Operation> getClientOperations(@PathVariable(name = "user_id") String userId) {
        try {
            Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            e.getMessage();
            List<Operation> operations = new ArrayList<>();
            operations.add(new Operation());
            return operations;
        }
        return operationService.readByUserId(Integer.parseInt(userId));
    }

    @GetMapping(value = "/getoperationbyclient={user_id}/fromdate={date}")
    public List<Operation> getClientOperationsFromDate(@PathVariable(name = "user_id") String userId, @PathVariable(name = "date") java.sql.Date date) {
        try{
            Integer.parseInt(userId);
            java.sql.Date.valueOf(date.toLocalDate());
        } catch (IllegalArgumentException e) {
            e.getMessage();
            List<Operation> operations = new ArrayList<>();
            operations.add(new Operation());
            return operations;
        }

        List<Operation> operations = operationService.readByUserIdAndDateAfter(Integer.parseInt(userId), date);
        return operations;
    }

    @GetMapping(value = "/getoperationbyclient={user_id}/todate={date}")
    public List<Operation> getClientOperationsToDate(@PathVariable(name = "user_id") String userId, @PathVariable(name = "date") String date) {
        try{
            Integer.parseInt(userId);
            java.sql.Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            e.getMessage();
            List<Operation> operations = new ArrayList<>();
            operations.add(new Operation());
            return operations;
        }

        List<Operation> operations = operationService.readByUserIdAndDateBefore(Integer.parseInt(userId), java.sql.Date.valueOf(date));
        return operations;
    }

    @GetMapping(value = "/getoperationbyclient={user_id}/fromdate={dateStarted}/todate={dateEnded}")
    public List<Operation> getClientOperationsToDate(@PathVariable(name = "user_id") String userId,
                                                     @PathVariable(name = "dateStarted") String dateStarted,
                                                     @PathVariable(name = "dateEnded") String dateEnded) {
        try{
            Integer.parseInt(userId);
            java.sql.Date.valueOf(dateStarted);
            java.sql.Date.valueOf(dateEnded);
        } catch (IllegalArgumentException e) {
            e.getMessage();
            List<Operation> operations = new ArrayList<>();
            operations.add(new Operation());
            return operations;
        }

        List<Operation> operations = operationService.readbyUserIdAndDateAfterAndDateBefore(Integer.parseInt(userId),
                java.sql.Date.valueOf(dateStarted), java.sql.Date.valueOf(dateEnded));
        return operations;
    }

    //------------------------------------------------------------------------------------------------------------------
    //Part for transferMoney
    //------------------------------------------------------------------------------------------------------------------

    @PutMapping(value = "/transfermonetyfrom={user_sender_id}to={user_receiver_id}&amount={amount}")
    public String transferMoney(@PathVariable(name = "user_sender_id") String userSenderId,
                                @PathVariable(name = "user_receiver_id") String userReceiverId,
                                @PathVariable(name = "amount") String amount){
        try{
            Double.parseDouble(amount);
            Integer.parseInt(userSenderId);
            Integer.parseInt(userReceiverId);
            if (!clientRepository.existsById(Integer.parseInt(userSenderId))){
                return "Entered client sender ID does not exist in the databse.";
            } else if (!clientRepository.existsById(Integer.parseInt(userReceiverId))) {
                return "Entered client receiver ID does not exist in the databse.";
            }
        } catch (NumberFormatException e) {
            e.getMessage();
            return "Check entered value please. Current value is not appropriate.";
        }
        String updated = transferService.transferMoney(Integer.parseInt(userSenderId), Integer.parseInt(userReceiverId), Double.parseDouble(amount));
        operationService.createOperation(new Client(Integer.parseInt(userSenderId)), "transferMoneySend", Double.parseDouble(amount) * (-1), new Date());
        operationService.createOperation(new Client(Integer.parseInt(userReceiverId)), "transferMoneyReceive", Double.parseDouble(amount), new Date());
        return updated;
    }


}
