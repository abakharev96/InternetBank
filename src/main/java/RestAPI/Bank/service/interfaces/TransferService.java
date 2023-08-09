package RestAPI.Bank.service.interfaces;

import RestAPI.Bank.entity.Client;

public interface TransferService {
    String transferMoney(int clientSenderId, int clientReceiverId, double amount);
}
