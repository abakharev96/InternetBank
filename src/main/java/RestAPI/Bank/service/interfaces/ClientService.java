package RestAPI.Bank.service.interfaces;

import RestAPI.Bank.entity.Client;
import java.util.List;

public interface ClientService {
    List<Client> readAll();
    Client read(int id);
    String putMoney(Double amount, int id);
    String takeMoney(Double amount, int id);
    String sendMoneyToUser(Double amount, int idSender);
    String receiveMoneyFromUser(Double amount, int idReceiver);
}
