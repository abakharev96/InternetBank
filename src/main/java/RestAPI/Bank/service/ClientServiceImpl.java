package RestAPI.Bank.service;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.repository.ClientRepository;
import RestAPI.Bank.service.interfaces.ClientService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> readAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client read(int id) {
        if (!clientRepository.existsById(id)){
            return new Client();
        }
        return clientRepository.getOne(id);
    }

    @Override
    public String putMoney(Double amount, int id) {
        if (clientRepository.existsById(id)) {
            final Client client = clientRepository.getOne(id);
            client.setBalance(client.getBalance() + amount);
            clientRepository.save(client);
            return "Data was successfully updated.";
        }
        return "Please check ID. There is no such ID in the database";
    }

    @Override
    public String takeMoney(Double amount, int id) {
        if (clientRepository.existsById(id)) {
            final Client client = clientRepository.getOne(id);
            if (client.getBalance() < amount) {
                return "There is not enough money. Operation is cancelled.";
            }
            client.setBalance(client.getBalance() - amount);
            clientRepository.save(client);
            return "Data was successfully updated.";
        }
        return "Please check ID. There is no such ID in the database";
    }

    @Override
    public String sendMoneyToUser(Double amount, int idSender) {
        final Client client = clientRepository.getOne(idSender);
        client.setBalance(client.getBalance() - amount);
        clientRepository.save(client);
        return "Sending money is successfull. ";
    }

    @Override
    public String receiveMoneyFromUser(Double amount, int idReceiver) {
        final Client client = clientRepository.getOne(idReceiver);
        client.setBalance(client.getBalance() + amount);
        clientRepository.save(client);
        return "Sending money is successfull.";
    }


}
