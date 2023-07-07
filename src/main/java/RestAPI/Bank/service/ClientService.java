package RestAPI.Bank.service;

import RestAPI.Bank.entity.Client;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface ClientService {
    List<Client> readAll();
    Client read(int id);
    String putMoney(Double amount, int id);
    String takeMoney(Double amount, int id);
}
