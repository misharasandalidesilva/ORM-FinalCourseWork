package lk.ijse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
//    @JoinColumn(name = "reg_id", nullable = false)
    private Registration registration;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    public Payment(int paymentId, Date paymentDate, double amount, String paymentMethod, Registration registration) {
        this.paymentId=paymentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.registration = registration;
    }
}
