package RestAPI.Bank.repository;

import RestAPI.Bank.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {
}
