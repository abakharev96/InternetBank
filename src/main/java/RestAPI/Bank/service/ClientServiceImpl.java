package RestAPI.Bank.service;

import RestAPI.Bank.entity.Client;
import RestAPI.Bank.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

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


}
