package RestAPI.Bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "transfer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transfer {
    @Id
    @Column(name = "transfer_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int transferId;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "user_sender_id", unique = true)
    private Client clientSender;

    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "user_receiver_id", unique = true)
    private Client clientReceiver;

    @Column(name = "amount")
    private Double amount;

    public Transfer() {

    }

    public Transfer(int transferId, Client clientSender, Client clientReceiver, Double amount) {
        this.transferId = transferId;
        this.clientSender = clientSender;
        this.clientReceiver = clientReceiver;
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public Client getClientSender() {
        return clientSender;
    }

    public void setClientSender(Client clientSender) {
        this.clientSender = clientSender;
    }

    public Client getClientReceiver() {
        return clientReceiver;
    }

    public void setClientReceiver(Client clientReceiver) {
        this.clientReceiver = clientReceiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
