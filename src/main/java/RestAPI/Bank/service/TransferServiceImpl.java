package RestAPI.Bank.service;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.entity.Transfer;
import RestAPI.Bank.repository.ClientRepository;
import RestAPI.Bank.repository.TransferRepository;
import RestAPI.Bank.service.interfaces.ClientService;
import RestAPI.Bank.service.interfaces.TransferService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final TransferRepository transferRepository;

    public TransferServiceImpl(ClientRepository clientRepository, ClientService clientService, TransferRepository transferRepository) {
        this.clientRepository = clientRepository;
        this.clientService = clientService;
        this.transferRepository = transferRepository;
    }

    @Override
    public String transferMoney(int clientSenderId, int clientReceiverId, double amount) {
        if (clientRepository.existsById(clientSenderId) & clientRepository.existsById(clientReceiverId)){
            final Client clientSender = clientRepository.getOne(clientSenderId);
            final Client clientReceiver = clientRepository.getOne(clientReceiverId);
            if (clientSender.getBalance() < amount) {
                return "User " + clientSenderId + " do not have enough money. Operation is cancelled. ";
            }
            String updateSend = clientService.sendMoneyToUser(amount, clientSenderId);
            String updateReceive = clientService.receiveMoneyFromUser(amount, clientReceiverId);
            createTransfer(clientSender, clientReceiver, amount);
            return "Transaction is completed. " + updateSend + updateReceive;
        }
        return "Please check users ID. There is no such ID in the database.";
    }

    @Override
    public void createTransfer(Client clientSender, Client clientReceiver, double amount) {
        Transfer transfer = new Transfer();
        transfer.setClientSender(clientSender);
        transfer.setClientReceiver(clientReceiver);
        transfer.setAmount(amount);
        transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> readAllTransfers() {
        List<Transfer> transfers = transferRepository.findAll();
        if (transfers.isEmpty()) {
            transfers.add(new Transfer());
        }
        return transfers;
    }

}
