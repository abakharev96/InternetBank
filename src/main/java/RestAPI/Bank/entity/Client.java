package RestAPI.Bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "clients")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;
    @Column(name = "user_balance")
    private double balance;

    public Client() {

    }

    public Client(Integer id) {
        this.id = id;
    }

    public Client(Integer id, Double balance) {
        this.id = id;
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
