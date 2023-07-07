package RestAPI.Bank.controller;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private ClientService clientService;

    public Controller(ClientService clientService) {
        this.clientService = clientService;
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
                "3. takeMoney " + "\n" +
                "You can call it as /client={user_id}&decramount={amount}. " + "\n" +
                "Example: /client=3&decramount=30 for the client with id equals 3 and that want to take 30 money.";
        return welcome;
    }

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
        } catch (NumberFormatException e) {
            e.getMessage();
            return "Check entered value please. Current value is not appropriate.";
        }
        String updated = clientService.putMoney(Double.parseDouble(amount), Integer.parseInt(id));
        return updated;
    }

    @PutMapping(value = "/client={user_id}&decramount={amount}")
    public String updateDecr(@PathVariable(name = "user_id") String id, @PathVariable(name = "amount") String amount) {
        try{
            Double.parseDouble(amount);
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.getMessage();
            return "Check entered value please. Current value is not appropriate.";
        }
        String updated = clientService.takeMoney(Double.parseDouble(amount), Integer.parseInt(id));
        return updated;
    }

}
