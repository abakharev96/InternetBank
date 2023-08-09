package RestAPI.Bank.service;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.repository.ClientRepository;
import RestAPI.Bank.service.interfaces.ClientService;
import RestAPI.Bank.service.interfaces.TransferService;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {
    private final ClientRepository clientRepository;
    private final ClientService clientService;

    public TransferServiceImpl(ClientRepository clientRepository, ClientService clientService) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @Override
    public String transferMoney(int clientSenderId, int clientReceiverId, double amount) {
        if (clientRepository.existsById(clientSenderId) & clientRepository.existsById(clientReceiverId)){
            final Client clientSender = clientRepository.getOne(clientSenderId);
            final Client clientReceiver = clientRepository.getOne(clientSenderId);
            if (clientSender.getBalance() < amount) {
                return "User " + clientSenderId + " do not have enough money. Operation is cancelled. ";
            }
            String updateSend = clientService.sendMoneyToUser(amount, clientSenderId);
            String updateReceive = clientService.receiveMoneyFromUser(amount, clientReceiverId);
            return "Transaction is completed. " + updateSend + updateReceive;
        }
        return "Please check users ID. There is no such ID in the database.";
    }
}
