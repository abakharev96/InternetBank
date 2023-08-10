package RestAPI.Bank.service.interfaces;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.entity.Transfer;

import java.util.List;

public interface TransferService {
    String transferMoney(int clientSenderId, int clientReceiverId, double amount);
    void createTransfer(Client clientSender, Client clientReceiver, double amount);
    List<Transfer> readAllTransfers();
}
