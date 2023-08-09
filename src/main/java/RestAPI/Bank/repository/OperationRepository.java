package RestAPI.Bank.repository;

import RestAPI.Bank.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
    String queryOperationsByClientId = "select operations from Operation operations where operations.client.id=?1";
    @Query(queryOperationsByClientId)
    List<Operation> findOperationsByClientId(@Param("operations.client.id") String userId);

    String queryOperationsByClientIdAndDateAfter = "select operations from Operation operations where operations.client.id=?1 and operations.date>=?2";
    @Query(queryOperationsByClientIdAndDateAfter)
    List<Operation> findOperationsByClientIdAndDateAfter(@Param("operations.client.id") String userId, @Param("operations.date") java.sql.Date date);

    String queryOperationsByClientIdAndDateBefore = "select operations from Operation operations where operations.client.id=?1 and operations.date<=?2";
    @Query(queryOperationsByClientIdAndDateBefore)
    List<Operation> findOperationsByClientIdAndDateBefore(@Param("operations.client.id") String userId, @Param("operations.date") java.sql.Date date);

    String queryOperationsByClientIdAndDateAfterAndDateBefore = "select operations from Operation operations " +
            "where operations.client.id=?1 and operations.date>=?2 and operations.date<=?3";
    @Query(value = queryOperationsByClientIdAndDateAfterAndDateBefore)
    List<Operation> findOperationsByClientIdAndDateAfterAndDateBefore(@Param("operations.client.id") String userId,
                                                                      @Param("operations.date") java.sql.Date dateStart,
                                                                      @Param("operations.date") java.sql.Date dateEnd);



}
