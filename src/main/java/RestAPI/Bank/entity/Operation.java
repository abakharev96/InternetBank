package RestAPI.Bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "operation_list")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Operation {

    @Id
    @Column(name = "operation_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int operationId;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "user_id", unique = true)
    //@Column(name = "user_id")
    private Client client;
    @Column(name = "operation_type")
    private String operationType;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "date")
    private Date date;

    public Operation() {

    }

    public Operation(int operationId, Client client, String operationType, Double amount, Date date) {
        this.operationId = operationId;
        this.client = client;
        this.operationType = operationType;
        this.amount = amount;
        this.date = date;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
